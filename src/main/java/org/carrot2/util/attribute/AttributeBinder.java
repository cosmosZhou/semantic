
/*
 * Carrot2 project.
 *
 * Copyright (C) 2002-2012, Dawid Weiss, Stanisław Osiński.
 * All rights reserved.
 *
 * Refer to the full license file "carrot2.LICENSE"
 * in the root folder of the repository checkout or at:
 * http://www.carrot2.org/carrot2.LICENSE
 */

package org.carrot2.util.attribute;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

import org.carrot2.util.attribute.constraint.*;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.google.common.primitives.Primitives;

/**
 * Provides methods for binding (setting and getting) values of attributes defined by the
 * {@link Attribute} annotation.
 */
public class AttributeBinder
{
    /** Default consistency check to apply before binding. */
    private final static Predicate<Field> CONSISTENCY_CHECKS = Predicates.and(
        new ConsistencyCheckRequiredAnnotations(),
        new ConsistencyCheckImplementingClasses());

    /**
     * Sets {@link Attribute} values on the provided <code>instance</code>.
     * <p>
     * Setting of attribute values will be performed for all attributes of the provided
     * <code>object</code>, no matter where in the <code>object</code>'s hierarchy the
     * attribute is declared. This method will recursively descend into all fields of the
     * <code>object</code> whose types are marked with {@link Bindable}, no matter whether
     * these fields are attributes or not.
     * <p>
     * Keys of the <code>values</code> map are interpreted as attribute keys as defined by
     * {@link Attribute#key()}. When setting attribute values, the map must contain non-
     * <code>null</code> mappings for all {@link Required} attributes that have not yet
     * been set on the <code>object</code> to a non-<code>null</code> value. Otherwise an
     * {@link AttributeBindingException} will be thrown. If the map has no mapping for
     * some non-{@link Required} attribute, the value of that attribute will not be
     * changed. However, if the map contains a <code>null</code> mapping for some non-
     * {@link Required} attribute, the value that attribute will be set to
     * <code>null</code>.
     * <p>
     * When setting attributes, values will be transferred from the map without any
     * conversion with two exceptions:
     * <ol>
     * <li>If the type of the value is {@link String} and the type of the attribute field
     * is not {@link String}, the {@link AttributeTransformerFromString} will be applied
     * to the value prior to transferring it to the attribute field. If you want to bypass
     * this conversion, use {@link #bind(Object, IAttributeBinderAction[], Class...)}.</li>
     * <li>If the type of the attribute field is not {@link Class} and the corresponding
     * value in the <code>values</code> map is of type {@link Class}, an attempt will be
     * made to coerce the class to a corresponding instance by calling its parameterless
     * constructor. If the created type is {@link Bindable}, an attempt will also be made
     * to set attributes of the newly created object using the <code>values</code> map and
     * current <code>filteringAnnotations</code>.</li>
     * </ol>
     * <p>
     * Before value of an attribute is set, the new value is checked against all
     * constraints defined for the attribute and must meet all these constraints.
     * Otherwise, the {@link ConstraintViolationException} will be thrown.
     * <p>
     * 
     * @param object the object to set the attributes on. The type of the provided object
     *            must be annotated with {@link Bindable}.
     * @param values the values of attributes to be set.
     * @param filteringAnnotations additional domain-specific annotations that the
     *            attribute fields must have in order to be bound. This parameter can be
     *            used to selectively set different sets of attributes depending, e.g. on
     *            the life cycle of the <code>object</code>.
     * @return entries from the <code>values</code> map that did not get transferred to
     *         any of the attributes.
     * @throws InstantiationException if coercion of a class attribute value to an
     *             instance fails, e.g. because the parameterless constructor is not
     *             present/ visible.
     * @throws AttributeBindingException if in the <code>values</code> map there are no or
     *             <code>null</code> values provided for one or more {@link Required}
     *             attributes.
     * @throws AttributeBindingException reflection-based setting or reading field values
     *             fails.
     * @throws IllegalArgumentException if <code>object</code>'s type is not
     *             {@link Bindable}.
     * @throws IllegalArgumentException for debugging purposes, if an attribute field is
     *             found that is missing some of the required annotations.
     * @throws UnsupportedOperationException if an attempt is made to bind values of
     *             attributes with circular references.
     */
    @SafeVarargs
    public static Map<String, Object> set(Object object, Map<String, Object> values,
        Class<? extends Annotation>... filteringAnnotations)
        throws InstantiationException, AttributeBindingException
    {
        return set(object, values, true, filteringAnnotations);
    }

    /**
     * A version of {@link #set(Object, Map, Class...)} that can optionally skip
     * {@link Required} attribute checking. For experts only.
     */
    @SafeVarargs
    public static <T> Map<String, Object> set(T object, Map<String, Object> values,
        boolean checkRequired, Class<? extends Annotation>... filteringAnnotations)
        throws InstantiationException, AttributeBindingException
    {
        return set(object, values, checkRequired,
            filteringAnnotations.length > 0 ? new AllAnnotationsPresentPredicate(
                filteringAnnotations) : Predicates.<Field> alwaysTrue());
    }

    /**
     * A version of {@link #set(Object, Map, boolean, Class...)} with a {@link Predicate}
     * instead of filtering annotations. For experts only.
     */
    public static Map<String, Object> set(Object object, Map<String, Object> values,
        boolean checkRequired, Predicate<Field> predicate) throws InstantiationException,
        AttributeBindingException
    {
        final AttributeBinderActionBind attributeBinderActionBind = 
            new AttributeBinderActionBind(
                values, 
                checkRequired, 
                AttributeTransformerFromString.INSTANCE,
                AttributeTransformerFactory.INSTANCE);
        final IAttributeBinderAction [] actions = new IAttributeBinderAction []
        {
            attributeBinderActionBind,
        };

        bind(object, actions, predicate);

        return attributeBinderActionBind.remainingValues;
    }

    /**
     * Gets (collects) {@link Attribute} values from the provided <code>instance</code>
     * into the provided {@link Map}.
     * <p>
     * Values will be collected for all attributes of the provided <code>object</code>, no
     * matter where in the <code>object</code>'s hierarchy the attribute is declared. This
     * method will recursively descend into all fields of the <code>object</code> whose
     * types are marked with {@link Bindable}, no matter whether these fields are
     * attributes or not.
     * <p>
     * Values of each attribute, including <code>null</code> values, will be stored in the
     * provided map under a key equal to the attribute's as defined by
     * {@link Attribute#key()}.
     * 
     * @param object the object to collect attributes from. The type of the provided
     *            object must be annotated with {@link Bindable}.
     * @param values placeholder for attributes to be collected. The provided Map must be
     *            modifiable.
     * @param filteringAnnotations additional domain-specific annotations that the
     *            attribute fields must have in order to be bound. This parameter can be
     *            used to selectively collect different sets of attributes depending, e.g.
     *            on the life cycle of the <code>object</code>.
     * @throws AttributeBindingException if reflection-based reading of field values
     *             fails.
     * @throws IllegalArgumentException if <code>object</code>'s type is not
     *             {@link Bindable}.
     * @throws IllegalArgumentException for debugging purposes, if an attribute field is
     *             found that is missing some of the required annotations.
     * @throws UnsupportedOperationException if an attempt is made to collect values of
     *             attributes with circular references.
     */
    @SafeVarargs
    public static void get(Object object, Map<String, Object> values,
        Class<? extends Annotation>... filteringAnnotations)
        throws InstantiationException, AttributeBindingException
    {
        final IAttributeBinderAction [] actions = new IAttributeBinderAction []
        {
            new AttributeBinderActionCollect(values),
        };

        bind(object, actions, filteringAnnotations);
    }

    /**
     * A generic method for performing actions on the <code>object</code>'s hierarchy of
     * attributes. For experts only.
     */
    @SafeVarargs
    public static void bind(Object object,
        IAttributeBinderAction [] attributeBinderActions,
        Class<? extends Annotation>... filteringAnnotations)
        throws InstantiationException, AttributeBindingException
    {
        bind(object, attributeBinderActions,
            filteringAnnotations.length > 0 ? new AllAnnotationsPresentPredicate(
                filteringAnnotations) : Predicates.<Field> alwaysTrue());
    }

    /**
     * A generic method for performing actions on the <code>object</code>'s hierarchy of
     * attributes. For experts only.
     */
    public static void bind(Object object,
        IAttributeBinderAction [] attributeBinderActions, Predicate<Field> predicate)
        throws InstantiationException, AttributeBindingException
    {
        bind(new HashSet<Object>(), new BindingTracker(), 0, object,
            attributeBinderActions,
            Predicates.<Field> and(CONSISTENCY_CHECKS, predicate), Bindable.class);
    }

    /**
     * A predicate that evaluates to <code>true</code> if the attribute is annotated with
     * <strong>all</strong> of the provided annotations.
     */
    public static class AllAnnotationsPresentPredicate implements Predicate<Field>
    {
        private final Class<? extends Annotation> [] filteringAnnotations;

        @SafeVarargs
        public AllAnnotationsPresentPredicate(
            Class<? extends Annotation>... filteringAnnotations)
        {
            this.filteringAnnotations = filteringAnnotations;
        }

        public boolean apply(Field field)
        {
            for (Class<? extends Annotation> annotation : filteringAnnotations)
            {
                if (field.getAnnotation(annotation) == null)
                {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * A generic method for performing actions on the <code>object</code>'s hierarchy of
     * attributes. Allows to customize the bindable marker annotation class. For experts
     * only.
     */
    static void bind(Object object, IAttributeBinderAction [] attributeBinderActions,
        Predicate<Field> predicate, Class<? extends Annotation> markerAnnotation)
        throws InstantiationException, AttributeBindingException
    {
        bind(new HashSet<Object>(), new BindingTracker(), 0, object,
            attributeBinderActions, predicate, markerAnnotation);
    }

    /**
     * Internal implementation that tracks object that have already been bound.
     */
    private static void bind(Set<Object> boundObjects, BindingTracker bindingTracker,
        int level, Object object, IAttributeBinderAction [] attributeBinderActions,
        Predicate<Field> predicate, Class<? extends Annotation> markerAnnotation)
        throws InstantiationException, AttributeBindingException
    {
        // We can only bind values on classes that are marked with the interface
        if (object.getClass().getAnnotation(markerAnnotation) == null)
        {
            throw new IllegalArgumentException("Class: " + object.getClass().getName()
                + " is not bindable, @" + markerAnnotation.getSimpleName() + " expected.");
        }

        // For keeping track of circular references
        boundObjects.add(object);

        // Get all fields (including those from bindable super classes)
        final Collection<Field> fieldSet = BindableUtils.getFieldsFromHierarchy(
            object.getClass(), markerAnnotation);

        for (final Field field : fieldSet)
        {
            Object value = null;

            // Omit any static fields.
            if (Modifier.isStatic(field.getModifiers())) {
              continue;
            }

            if (!Modifier.isPublic(field.getModifiers())) {
              if (field.getAnnotation(Attribute.class) != null) {
                throw AttributeBindingException.createWithNoKey("Non-public attribute fields are no longer supported: "
                    + field.getDeclaringClass().getName() + "#" + field.getName());
              }

              if (predicate.apply(field)) {
                throw AttributeBindingException.createWithNoKey("Non-public fields are no longer supported: "
                    + field.getDeclaringClass().getName() + "#" + field.getName());
              }

              assert BindableDescriptorBuilder.noHiddenBindables(field, object, markerAnnotation);
              continue;
            }

            // Get the value to perform a recursive call on it later on
            try
            {
                value = field.get(object);
            }
            catch (final IllegalAccessException e)
            {
                throw AttributeBindingException.createWithNoKey("Non-accessible field: "
                    + object.getClass().getName() + "#" + field.getName(), e);
            }
            catch (final Exception e)
            {
                throw AttributeBindingException.createWithNoKey("Could not get field value "
                        + object.getClass().getName() + "#" + field.getName());
            }

            // We skip fields that do not have all the required annotations
            if (predicate.apply(field))
            {
                try
                {
                    // Apply binding actions provided
                    for (int i = 0; i < attributeBinderActions.length; i++)
                    {
                        attributeBinderActions[i].performAction(bindingTracker, level,
                            object, field, value, predicate);
                    }

                    // The value may have changed as a result of binding, so we need
                    // to re-read it here. Otherwise, the recursive descent below
                    // would bind values to an abandoned reference obtained at the
                    // top of this method.
                    value = field.get(object);
                }
                catch (ConstraintViolationException e)
                {
                    throw AttributeBindingException.createWithNoKey(e.getMessage(), e);
                }
                catch (AttributeBindingException e)
                {
                    // Rethrow the original binding exception.
                    throw e;
                }
                catch (Exception e)
                {
                    throw AttributeBindingException.createWithNoKey(
                        "Could not get field value " + object.getClass().getName() + "#"
                            + field.getName(), e);
                }
            }

            // If value is not null and its class is @Bindable, we must descend into it
            if (value != null && value.getClass().getAnnotation(markerAnnotation) != null)
            {
                // Check for circular references
                if (boundObjects.contains(value))
                {
                    throw new UnsupportedOperationException(
                        "Circular references are not supported");
                }

                // Recursively descend into other types.
                bind(boundObjects, bindingTracker, level + 1, value,
                    attributeBinderActions, predicate, markerAnnotation);
            }
        }
    }

    /**
     * An action to be applied during attribute binding.
     */
    public static interface IAttributeBinderAction
    {
        public void performAction(BindingTracker bindingTracker, int level,
            Object object, Field field, Object value, Predicate<Field> predicate)
            throws InstantiationException;
    }

    /**
     * Transforms attribute values.
     */
    public static interface IAttributeTransformer
    {
        public Object transform(Object value, String key, Field field);
    }
    
    /**
     * Transforms {@link String} attribute values to the types required by the target
     * field by:
     * <ol>
     * <li>Leaving non-{@link String} typed values unchanged.</li>
     * <li>Looking for a static <code>valueOf(String)</code> in the target type and using
     * it for conversion.</li>
     * <li>If the method is not available, trying to load a class named as the value of
     * the attribute, so that this class can be further coerced to the class instance.</li>
     * <li>If the class cannot be loaded, leaving the the value unchanged.</li>
     * </ol>
     */
    public static class AttributeTransformerFromString implements IAttributeTransformer
    {
        /** Shared instance of the transformer. */
        public static final AttributeTransformerFromString INSTANCE = new AttributeTransformerFromString();

        /**
         * Private constructor, use {{@link #INSTANCE}.
         */
        private AttributeTransformerFromString()
        {
        }

        public Object transform(Object value, String key, Field field)
        {
            if (!(value instanceof String))
            {
                return value;
            }

            final String stringValue = (String) value;
            final Class<?> fieldType = Primitives.wrap(field.getType());
            if (String.class.equals(fieldType))
            {
                // Return Strings unchanged
                return stringValue;
            }
            else
            {

                // Try valueOf(String) on the declared type
                Object convertedValue = null;
                convertedValue = callValueOf(stringValue, fieldType);
                if (convertedValue != null)
                {
                    return convertedValue;
                }

                // Try valueOf(String) of the declared implementing classes, useful
                // when field type is an interface, which is probably a common case.
                // We process implementing classes in the order they appear in the
                // annotation, which means we'll transform to an instance of the first
                // class that returns a non-null valueOf(String).
                final ImplementingClasses implementingClasses = field
                    .getAnnotation(ImplementingClasses.class);
                if (implementingClasses != null)
                {
                    final Class<?> [] classes = implementingClasses.classes();
                    for (Class<?> toClass : classes)
                    {
                        convertedValue = callValueOf(stringValue, toClass);
                        if (convertedValue != null)
                        {
                            return convertedValue;
                        }
                    }
                }

                /*
                 * Try if we can assign anyway. If the attribute is of a non-primitive
                 * type, it must have an ImplementingClasses annotation, see
                 * ConsistencyCheckImplementingClasses. If the value meets the constraint,
                 * we'll return the original value.
                 */
                if (implementingClasses != null
                    && field.getType().isAssignableFrom(String.class)
                    && ConstraintValidator.isMet(stringValue, implementingClasses).length == 0)
                {
                    return stringValue;
                }

                // Try loading the class indicated by this string.
                try
                {
                    return Class.forName(stringValue, true, 
                        Thread.currentThread().getContextClassLoader());
                }
                catch (SecurityException e)
                {
                  // Ignore, no access to CCL.
                }
                catch (ClassNotFoundException e)
                {
                  // Just skip this possibility.
                }

                return stringValue;
            }
        }

        private Object callValueOf(final String stringValue, final Class<?> fieldType)
        {
            try
            {
                final Method valueOfMethod = fieldType.getMethod("valueOf", String.class);
                return valueOfMethod.invoke(null, stringValue);
            }
            catch (NoSuchMethodException e)
            {
                return null;
            }
            catch (IllegalAccessException e)
            {
                throw new RuntimeException("No access to valueOf() method in: "
                    + fieldType.getName());
            }
            catch (InvocationTargetException e)
            {
                final Throwable target = e.getTargetException();
                if (target instanceof NumberFormatException)
                {
                    return null;
                }
                else
                {
                    throw Throwables.propagate(target);
                }
            }
        }
    }

    /**
     * An action that binds all {@link Input} attributes.
     */
    public static class AttributeBinderActionBind implements IAttributeBinderAction
    {
        private final Map<String, Object> values;
        public final Map<String, Object> remainingValues;
        private final boolean checkRequired;
        private final IAttributeTransformer [] transformers;

        public AttributeBinderActionBind(Map<String, Object> values,
            boolean checkRequired, IAttributeTransformer... transformers)
        {
            this.values = values;
            this.checkRequired = checkRequired;
            this.transformers = transformers;
            this.remainingValues = Maps.newHashMap(values);
        }

        public void performAction(BindingTracker bindingTracker, int level,
            Object object, Field field, Object value, Predicate<Field> predicate)
            throws InstantiationException
        {
            final boolean required = field.getAnnotation(Required.class) != null
                && checkRequired;
            final Object currentValue = value;
            final String key = BindableUtils.getKey(field);

            // Transfer values from the map to the fields. If the input map
            // doesn't contain an entry for this key, do nothing. Otherwise,
            // perform binding as usual. This will allow to set null values
            if (!values.containsKey(key))
            {
                if (currentValue == null && required)
                {
                    // Throw exception only if the current value is null
                    throw new AttributeBindingException(key,
                        "No value for required attribute: " + key + " ("
                            + field.getDeclaringClass().getName() + "#" + field.getName()
                            + ")");
                }
                return;
            }

            // Note that the value can still be null here
            value = values.get(key);

            if (required)
            {
                if (value == null)
                {
                    throw new AttributeBindingException(key,
                        "Not allowed to set required attribute to null: " + key);
                }
            }

            // Apply value transformers before any other checks, conversions
            // to allow type-changing transformations as well.
            for (IAttributeTransformer transformer : transformers)
            {
                value = transformer.transform(value, key, field);
            }

            // Try to coerce from class to its instance first
            // Notice that if some extra annotations are provided, the newly
            // created instance will get only those attributes bound that
            // match any of the extra annotations.
            if (Class.class.isInstance(value) && !field.getType().equals(Class.class))
            {
                final Class<?> clazz = ((Class<?>) value);
                try
                {
                    value = clazz.newInstance();
                    if (clazz.isAnnotationPresent(Bindable.class))
                    {
                        set(value, values, false, predicate);
                    }
                }
                catch (Throwable e)
                {
                    String message = null;
                    if (e instanceof IllegalAccessException
                        || e instanceof InstantiationException)
                    {
                        message = detailedExceptionInfo(clazz);
                    }

                    final InstantiationException ie = new InstantiationException(
                        "Could not create instance of class: " + clazz.getName()
                            + " for attribute " + key
                            + (message != null ? ": " + message : ""));
                    ie.initCause(e);
                    throw ie;
                }
            }

            if (value != null)
            {
                // Check constraints
                final Annotation [] unmetConstraints = ConstraintValidator.isMet(value,
                    field.getAnnotations());
                if (unmetConstraints.length > 0)
                {
                    throw new ConstraintViolationException(key, value, unmetConstraints);
                }
            }

            // Finally, set the field value
            try
            {
                field.set(object, value);
            }
            catch (final Exception e)
            {
                throw new AttributeBindingException(key, "Could not assign field "
                    + object.getClass().getName() + "#" + field.getName()
                    + " with value " + value, e);
            }

            remainingValues.remove(key);
        }

        /**
         * Return a somewhat more detailed reason why instantiation couldn't progress.
         */
        private String detailedExceptionInfo(Class<?> clazz)
        {
            if (!Modifier.isPublic(clazz.getModifiers())) return "Class "
                + clazz.getName() + " is not public.";

            if (clazz.isMemberClass() && !Modifier.isStatic(clazz.getModifiers()))
            {
                return "Nested class " + clazz.getName() + " is not static.";
            }

            try
            {
                clazz.getConstructor(new Class<?> [0]);
            }
            catch (Exception e)
            {
                return "Class " + clazz.getName()
                    + " must have a public parameterless constructor.";
            }

            return null;
        }
    }

    /**
     * An action that binds all {@link Output} attributes.
     */
    public static class AttributeBinderActionCollect implements IAttributeBinderAction
    {
        final private Map<String, Object> values;
        final IAttributeTransformer [] transformers;

        public AttributeBinderActionCollect(Map<String, Object> values,
            IAttributeTransformer... transformers)
        {
            this.values = values;
            this.transformers = transformers;
        }

        public void performAction(BindingTracker bindingTracker, int level,
            Object object, Field field, Object value, Predicate<Field> predicate)
            throws InstantiationException
        {
            final String key = BindableUtils.getKey(field);
            try
            {

                // Apply transforms
                for (IAttributeTransformer transformer : transformers)
                {
                    value = transformer.transform(value, key, field);
                }

                if (bindingTracker.canBind(object, key, level))
                {
                    values.put(key, value);
                }
            }
            catch (final Exception e)
            {
                throw new AttributeBindingException(key, "Could not get field value "
                    + object.getClass().getName() + "#" + field.getName(), e);
            }
        }
    }

    /**
     * Checks if all required attribute annotations are provided.
     */
    static final class ConsistencyCheckRequiredAnnotations implements Predicate<Field>
    {
        @Override
        public boolean apply(Field field)
        {
            final boolean hasAttribute = field.getAnnotation(Attribute.class) != null;
            boolean hasBindingDirection = field.getAnnotation(Input.class) != null
                || field.getAnnotation(Output.class) != null;

            if (hasAttribute)
            {
                if (!hasBindingDirection)
                {
                    throw new IllegalArgumentException(
                        "Define binding direction annotation (@"
                            + Input.class.getSimpleName() + " or @"
                            + Output.class.getSimpleName() + ") for field "
                            + field.getClass().getName() + "#" + field.getName());
                }
            }
            else
            {
                if (hasBindingDirection)
                {
                    throw new IllegalArgumentException(
                        "Binding  direction defined for a field (" + field.getClass()
                            + "#" + field.getName() + ") that does not have an @"
                            + Attribute.class.getSimpleName() + " annotation");
                }
            }

            return hasAttribute;
        }
    }

    /**
     * Checks whether attributes of non-primitive types have the
     * {@link ImplementingClasses} constraint.
     */
    static final class ConsistencyCheckImplementingClasses implements Predicate<Field>
    {
        static Set<Class<?>> ALLOWED_PLAIN_TYPES = ImmutableSet.<Class<?>> of(File.class);

        static Set<Class<?>> ALLOWED_ASSIGNABLE_TYPES = ImmutableSet.<Class<?>> of(
            Enum.class, IAssignable.class, Collection.class, Map.class);

        @Override
        public boolean apply(Field field)
        {
            if (field.getAnnotation(Input.class) == null)
            {
                return true;
            }

            final Class<?> attributeType = Primitives.wrap(field.getType());
            if (Modifier.isFinal(attributeType.getModifiers()))
            {
                return true;
            }

            if (!ALLOWED_PLAIN_TYPES.contains(attributeType)
                && !isAllowedAssignableType(attributeType)
                && field.getAnnotation(ImplementingClasses.class) == null)
            {
                throw new IllegalArgumentException("Non-primitive typed attribute "
                    + field.getDeclaringClass().getName() + "#" + field.getName()
                    + " must have the @" + ImplementingClasses.class.getSimpleName()
                    + " constraint.");
            }

            return true;
        }

        private static boolean isAllowedAssignableType(Class<?> attributeType)
        {
            for (Class<?> clazz : ALLOWED_ASSIGNABLE_TYPES)
            {
                if (clazz.isAssignableFrom(attributeType))
                {
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * Tracks which attributes have already been collected and prevents overwriting of
     * collected values.
     */
    public static final class BindingTracker
    {
        /**
         * The lowest nesting level from which the attribute has been collected.
         */
        private Map<String, Integer> bindingLevel = Maps.newHashMap();

        /**
         * Containing instance + attribute key pairs that have already been collected.
         */
        private Set<Pair<Object, String>> boundInstances = Sets.newHashSet();

        boolean canBind(Object instance, String key, int level)
        {
            final Pair<Object, String> pair = new Pair<Object, String>(instance, key);
            if (boundInstances.contains(pair))
            {
                throw AttributeBindingException.createWithNoKey(
                    "Collecting values of multiple attributes with the same key (" + key
                        + ") in the same instance of class ("
                        + instance.getClass().getName() + ") is not allowed");
            }
            boundInstances.add(pair);

            // We can collect this attribute if:
            // 1) it has not yet been collected or
            // 2) it has been collected at a deeper level of the nesting hierarchy
            // but we found another value for it found closer to the root object for
            // which binding is performed.
            final Integer boundAtLevel = bindingLevel.get(key);
            final boolean canBind = boundAtLevel == null
                || (boundAtLevel != null && boundAtLevel > level);
            if (canBind)
            {
                bindingLevel.put(key, level);
            }
            return canBind;
        }
    }
}
