package pub.tbc.mybatis.plugin;


/**
 * 强制类型转换工具
 *
 * @author tbc
 * @version 1.0 {2016年6月29日 下午5:14:55}
 */
public final class CastUtil {

    private CastUtil() {
        throw new AssertionError("No pub.tbc.toolkit.CastUtil instances for you!");
    }

    /**
     * 强转为String（默认值为""）
     *
     * @param obj
     * @return
     * @author tbc
     * @version 1.0 {2016年6月29日 下午5:59:31}
     */
    public static String castString(Object obj) {
        return CastUtil.castString(obj, "");
    }

    /**
     * 强转为String（提供默认值）
     *
     * @param obj
     * @param defaultValue
     * @return
     * @author tbc
     * @version 1.0 {2016年6月29日 下午5:59:41}
     */
    public static String castString(Object obj, String defaultValue) {
        return obj == null ? defaultValue : String.valueOf(obj);
    }

    /**
     * 强转为double（默认值为0）
     *
     * @param obj
     * @return
     * @author tbc
     * @version 1.0 {2016年6月29日 下午6:01:00}
     */
    public static double castDouble(Object obj) {
        return castDouble(obj, 0);
    }

    /**
     * 强转为double（提供默认值）
     *
     * @param obj
     * @param defaultValue
     * @return
     * @author tbc
     * @version 1.0 {2016年6月29日 下午6:00:05}
     */
    public static double castDouble(Object obj, double defaultValue) {
        double doubleValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (Objs.nonEmpty(strValue)) {
                try {
                    doubleValue = Double.parseDouble(strValue);
                } catch (NumberFormatException e) {
                    // 就是啥也不干，嗯，或者打个日志
                }
            }
        }
        return doubleValue;
    }

    /**
     * 强转long ，默认0
     *
     * @param obj
     * @return
     * @author tbc
     * @version 1.0 {2016年6月29日 下午6:05:23}
     */
    public static long castLong(Object obj) {
        return castLong(obj, 0);
    }

    /**
     * 强转long ，提供默认值
     *
     * @param obj
     * @param defaultValue
     * @return
     * @author tbc
     * @version 1.0 {2016年6月29日 下午6:02:49}
     */
    public static long castLong(Object obj, long defaultValue) {
        long longValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (Objs.nonEmpty(strValue)) {
                try {
                    longValue = Long.parseLong(strValue);
                } catch (NumberFormatException e) {
                    // 就是啥也不干，嗯，或者打个日志
                }
            }
        }
        return longValue;
    }

    /**
     * 强转int ，默认0
     *
     * @param obj
     * @return
     * @author tbc
     * @version 1.0 {2016年6月29日 下午6:05:23}
     */
    public static int castInt(Object obj) {
        return castInt(obj, 0);
    }

    /**
     * 强转int ，提供默认值
     *
     * @param obj
     * @param defaultValue
     * @return
     * @author tbc
     * @version 1.0 {2016年6月29日 下午6:02:49}
     */
    public static int castInt(Object obj, int defaultValue) {
        int intValue = defaultValue;
        if (obj != null) {
            String strValue = castString(obj);
            if (Objs.nonEmpty(strValue)) {
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (NumberFormatException e) {
                    // 就是啥也不干，嗯，或者打个日志
                }
            }
        }
        return intValue;
    }

    /**
     * 强转int ，默认0
     *
     * @param obj
     * @return
     * @author tbc
     * @version 1.0 {2016年6月29日 下午6:05:23}
     */
    public static boolean castBoolean(Object obj) {
        return castBoolean(obj, false);
    }

    /**
     * 强转int ，提供默认值
     *
     * @param obj
     * @param defaultValue
     * @return
     * @author tbc
     * @version 1.0 {2016年6月29日 下午6:02:49}
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if (obj != null) {
            booleanValue = Boolean.parseBoolean(castString(obj));
        }
        return booleanValue;
    }

    // public static <T> T caseGenericity(Object obj, T defaultValue){
    // T t = defaultValue;
    // if(obj != null)
    // }
}
