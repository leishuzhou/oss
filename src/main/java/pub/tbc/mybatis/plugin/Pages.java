package pub.tbc.mybatis.plugin;

/**
 * 工具类，提供一个ThreadLocal对象，用以保存分页数据
 * 注：在使用线程池的应用中，因为线程是复用的，因为需要清理ThreadLocal数据，以象产生
 * 已知问题：如果客户端没有调用get方法，值就不会清除，就会被带到下次线程复用的时候；
 *
 * @author tbc on 2016/10/16 1:18.
 */
public final class Pages {
    private static ThreadLocal<PageWrapper> pp = new ThreadLocal<>();

    static void add(PageWrapper pageWrapper) {
        pp.set(pageWrapper);
    }

    public static PageWrapper get() {
        PageWrapper pageWrapper = pp.get();
        pp.remove();
        return pageWrapper;
    }

}
