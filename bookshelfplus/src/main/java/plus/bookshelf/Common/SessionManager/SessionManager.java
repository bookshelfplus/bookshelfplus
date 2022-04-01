package plus.bookshelf.Common.SessionManager;

public abstract interface SessionManager {
    /**
     * 获取 Session
     * @param key
     * @return
     */
    Object getValue(String key);

    /**
     * 设置 Session
     * @param key
     * @param value
     */
    void setValue(String key, Object value);

    /**
     * 移除 Session
     * @param key
     */
    void remove(String key);
}
