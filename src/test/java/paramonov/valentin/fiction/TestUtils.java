package paramonov.valentin.fiction;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.List;

public final class TestUtils {
    public static Matcher<List<Integer>> listMatches(final Object... list) {
        return new BaseMatcher<List<Integer>>() {
            @Override
            @SuppressWarnings("unchecked")
            public boolean matches(Object o) {
                if(!(o instanceof List)) {
                    return false;
                }

                final List<Object> matchList;
                if(o instanceof List) {
                    matchList = (List<Object>) o;
                } else {
                    throw new IllegalArgumentException("List expected");
                }

                if(list.length != matchList.size()) {
                    return false;
                }

                for(int i = 0; i < list.length; i++) {
                    final Object dis = list[i];
                    final Object dat = matchList.get(i);
                    if(!dis.equals(dat)) {
                        return false;
                    }
                }

                return true;
            }

            @Override
            public void describeTo(Description description) {
                StringBuilder sb = new StringBuilder("<[");
                for(Object o : list) {
                    sb.append(o + ", ");
                }
                final int length = sb.length();
                if(length != 2) {
                    sb.setLength(length - 2);
                }
                sb.append("]>");
                description.appendText(sb.toString());
            }
        };
    }

    public static final List<Integer> toIntegerList(byte[] bytes) {
        List<Integer> integers = new ArrayList<>(bytes.length);

        for(byte b : bytes) {
            integers.add(0xff & b);
        }

        return integers;
    }

    public static final List<Integer> toIntegerList(int[] ints) {
        List<Integer> integers = new ArrayList<>(ints.length);

        for(int i : ints) {
            integers.add(i);
        }

        return integers;
    }
}
