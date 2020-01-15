package com.nlp;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.SystemUtils;

import com.deeplearning.CWSTagger;
import com.deeplearning.NERTaggerDict;
import com.deeplearning.Service;
import com.util.HttpClientWebApp;
import com.util.Utility;

@Path("algorithm")
public class Algorithm {
	static {
		if (SystemUtils.IS_OS_WINDOWS) {
			System.out.println("SystemUtils.IS_OS_WINDOWS");
			Utility.workingDirectory = "d:/360/solution/";
		} else {
			System.out.println("SystemUtils.IS_OS_LINUX");
			Utility.workingDirectory = "/home/v-rogenhxh/deeplearning/";
		}
	}

	@GET
	@Path("ahocorasick/{service}/{text}")
	@Produces("text/plain;charset=utf-8")
	public String ahocorasick(@PathParam("service") String service, @PathParam("text") String text) throws Exception {
		return Utility.jsonify(AhoCorasick.instance.ahocorasick(text, service));
	}

	@GET
	@Path("_ahocorasick/{service}/{text}")
	@Produces("text/plain;charset=utf-8")
	public String _ahocorasick(@PathParam("service") String service, @PathParam("text") String text) throws Exception {
		return Utility.jsonify(AhoCorasick.instance._ahocorasick(text, service));
	}

	@POST
	@Path("ahocorasick")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String ahocorasick(@Context HttpServletRequest request) throws Exception {
		String text = request.getParameter("text");

		String service = request.getParameter("service");
		return Utility.jsonify(AhoCorasick.instance.ahocorasick(text, service));
	}

	@POST
	@Path("_ahocorasick")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String _ahocorasick(@Context HttpServletRequest request) throws Exception {
		String text = request.getParameter("text");

		String service = request.getParameter("service");
		return Utility.jsonify(AhoCorasick.instance._ahocorasick(text, service));
	}

	@POST
	@Path("random_select")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String random_select(@Context HttpServletRequest request) throws Exception {

		String service = request.getParameter("service");
//		System.out.println("service = " + service);

		String exclude = request.getParameter("exclude");
//		System.out.println("exclude = " + exclude);
		
		String slot = request.getParameter("slot");
//		System.out.println("slot = " + slot);

		return AhoCorasick.instance.random_select(service, slot, exclude);
	}

	@GET
	@Path("python/ner/{service}/{text}")
	@Produces("text/plain;charset=utf-8")
	public String python_ner(@PathParam("service") String service, @PathParam("text") String text) throws Exception {
		String intent = Utility.jsonify(AhoCorasick.instance.ahocorasick(text, service));
		return HttpClientWebApp.instance.ner(text, service, intent);
	}

	@GET
	@Path("python/ner/{text}")
	@Produces("text/plain;charset=utf-8")
	public String python_ner(@PathParam("text") String text) throws Exception {
		String service = HttpClientWebApp.instance.service(text);
		String intent = Utility.jsonify(AhoCorasick.instance.ahocorasick(text, service));
		return HttpClientWebApp.instance.ner(text, service, intent);
	}

	@GET
	@Path("_python/ner/{service}/{text}")
	@Produces("text/plain;charset=utf-8")
	public String _python_ner(@PathParam("service") String service, @PathParam("text") String text) throws Exception {
		String intent = Utility.jsonify(AhoCorasick.instance._ahocorasick(text, service));
		return HttpClientWebApp.instance.ner(text, service, intent);
	}

	@POST
	@Path("python/ner")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String python_ner(@Context HttpServletRequest request) throws Exception {
		String text = request.getParameter("text");
		String service = request.getParameter("service");
		if (service == null) {
			service = HttpClientWebApp.instance.service(text);
		}

		String intent = Utility.jsonify(AhoCorasick.instance.ahocorasick(text, service));
		return HttpClientWebApp.instance.ner(text, service, intent);
	}

	@GET
	@Path("java/cws/{text}")
	@Produces("text/plain;charset=utf-8")
	public String cws(@PathParam("text") String text) throws Exception {
		return Utility.jsonify(CWSTagger.instance.cut(text));
	}

	@POST
	@Path("java/cws")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String cws(@Context HttpServletRequest request) throws Exception {
		return Utility.jsonify(CWSTagger.instance.cut(request.getParameter("text")));
	}

//	http://127.0.0.1:8080/nlp/algorithm/java/ner/map/播放下一站下一站传奇城乘风破浪
	@GET
	@Path("java/ner/{service}/{text}")
	@Produces("text/plain;charset=utf-8")
	public String java_ner(@PathParam("service") String service, @PathParam("text") String text) throws Exception {
		return Utility.jsonify(NERTaggerDict.predict(service, text, false));
	}

	@GET
	@Path("_java/ner/{service}/{text}")
	@Produces("text/plain;charset=utf-8")
	public String _java_ner(@PathParam("service") String service, @PathParam("text") String text) throws Exception {
		return Utility.jsonify(NERTaggerDict._predict(service, text, false));
	}

	@GET
	@Path("java/ner/{text}")
	@Produces("text/plain;charset=utf-8")
	public String java_ner(@PathParam("text") String text) throws Exception {

		String service = Service.instance.predict(text);

		if (service.indexOf('.') >= 0) {
			String[] service_code = service.split("\\.");
			service = service_code[0];
		}

		return Utility.jsonify(NERTaggerDict.predict(service, text, false));
	}

	@POST
	@Path("java/ner")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String java_ner(@Context HttpServletRequest request) throws Exception {
		String text = request.getParameter("text");
		String service = request.getParameter("service");
		if (service == null) {
			service = Service.instance.predict(text);
		}

		return Utility.jsonify(NERTaggerDict.predict(service, text, false));
	}

	@POST
	@Path("_java/ner")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String _java_ner(@Context HttpServletRequest request) throws Exception {
		String text = request.getParameter("text");
		String service = request.getParameter("service");
		if (service == null) {
			service = Service.instance.predict(text);
		}

		return Utility.jsonify(NERTaggerDict._predict(service, text, false));
	}

	@GET
	@Path("python/service/{text}")
	@Produces("text/plain;charset=utf-8")
	public String python_service(@PathParam("text") String text) throws Exception {
		return HttpClientWebApp.instance.service(text);
	}

	@POST
	@Path("python/service")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String python_service(@Context HttpServletRequest request) throws Exception {
		return HttpClientWebApp.instance.service(request.getParameter("text"));
	}

//	http://127.0.0.1:8080/nlp/algorithm/java/service/播放下一站下一站传奇城乘风破浪
	@GET
	@Path("java/service/{text}")
	@Produces("text/plain;charset=utf-8")
	public String java_service(@PathParam("text") String text) throws Exception {
		return Service.instance.predict(text);
	}

//	http://127.0.0.1:8080/nlp/algorithm/cpp/service/播放下一站下一站传奇城乘风破浪
	@GET
	@Path("cpp/service/{text}")
	@Produces("text/plain;charset=utf-8")
	public String cpp_service(@PathParam("text") String text) throws Exception {
		return Service.instance.cpp_predict(text);
	}

	@POST
	@Path("cpp/service")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String cpp_service(@Context HttpServletRequest request) throws Exception {
		return Service.instance.cpp_predict(request.getParameter("text"));
	}

//	http://127.0.0.1:8080/nlp/algorithm/cpp/ner/map/导航去山西省万荣县通化镇通化三村朝阳巷204号
	@GET
	@Path("cpp/ner/{service}/{text}")
	@Produces("text/plain;charset=utf-8")
	public String cpp_ner(@PathParam("service") String service, @PathParam("text") String text) throws Exception {
		return Utility.jsonify(NERTaggerDict.predict(service, text, true));
	}

//	http://127.0.0.1:8080/nlp/algorithm/cpp/ner/map/导航去山西省万荣县通化镇通化三村朝阳巷204号
	@GET
	@Path("cpp/ner/{text}")
	@Produces("text/plain;charset=utf-8")
	public String cpp_ner(@PathParam("text") String text) throws Exception {
		String service = Service.instance.cpp_predict(text);

		if (service.indexOf('.') >= 0) {
			String[] service_code = service.split("\\.");
			service = service_code[0];
		}

		return Utility.jsonify(NERTaggerDict.predict(service, text, true));
	}

	@POST
	@Path("cpp/ner")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String cpp_ner(@Context HttpServletRequest request) throws Exception {
		String service = request.getParameter("service");
		String text = request.getParameter("text");
		if (service == null) {
			service = Service.instance.cpp_predict(text);
		}
		return Utility.jsonify(NERTaggerDict.predict(service, text, true));
	}

	@POST
	@Path("_cpp/ner")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String _cpp_ner(@Context HttpServletRequest request) throws Exception {
		String service = request.getParameter("service");
		String text = request.getParameter("text");
		if (service == null) {
			service = Service.instance.cpp_predict(text);
		}

		return Utility.jsonify(NERTaggerDict._predict(service, text, true));
	}

	// http://127.0.0.1:8080/nlp/algorithm/java/_service/播放下一站下一站传奇城乘风破浪
	@GET
	@Path("_java/service/{text}")
	@Produces("text/plain;charset=utf-8")
	public String _java_service(@PathParam("text") String text) throws Exception {
		return Utility.jsonify(Service.instance._predict(text));
	}

	@POST
	@Path("java/service")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String java_service(@Context HttpServletRequest request) throws Exception {
		return Service.instance.predict(request.getParameter("text"));
	}

	@POST
	@Path("_java/service")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain;charset=utf-8")
	public String _java_service(@Context HttpServletRequest request) throws Exception {
		String text = request.getParameter("text");
		if (text != null)
			return Utility.jsonify(Service.instance._predict(text));
		String label = request.getParameter("label");
		if (label != null)
			return Utility.jsonify(Service.instance.label);
		return null;
	}

}
//cd /usr/local/tomcat/webapps/nlp/WEB-INF/classes/
//lcd ../nlp/target/classes/
//put -r com/