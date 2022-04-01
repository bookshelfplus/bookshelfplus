package plus.bookshelf.Common.SessionManager;

import javax.servlet.http.HttpServletRequest;

public class LocalSessionManager implements SessionManager {

    /**
     * 私有化构造函数
     */
    private LocalSessionManager(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    static SessionManager sessionManager = null;
    // static SessionManager sessionManager = new LocalSessionManager();

    /**
     * 通过此方法获取当前类的实例
     *
     * @return
     */
    public static SessionManager getInstance(HttpServletRequest httpServletRequest) {
        if (sessionManager == null)
            sessionManager = new LocalSessionManager(httpServletRequest);
        return sessionManager;
    }

    HttpServletRequest httpServletRequest;

    @Override
    public Object getValue(String key) {
        try {
            return httpServletRequest.getSession().getAttribute(key);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setValue(String key, Object value) {
        httpServletRequest.getSession().setAttribute(key, value);
    }

    @Override
    public void remove(String key) {
        httpServletRequest.getSession().removeAttribute(key);
    }
}
