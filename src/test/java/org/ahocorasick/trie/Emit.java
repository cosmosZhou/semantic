package org.ahocorasick.trie;

import org.ahocorasick.interval.Interval;
import org.ahocorasick.interval.Intervalable;

public class Emit extends Interval implements Intervalable {

    public final String value;

    public Emit(final int start, final int end, final String value) {
        super(start, end);
        this.value = value;
    }

    @Override
    public String toString() {
        return super.toString() + "=" + this.value;
    }

}
