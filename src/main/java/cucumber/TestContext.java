package cucumber;

import java.util.HashMap;
import java.util.Map;

public class TestContext {

    private Map<String, Object> testContext;

    public TestContext(){
        this.testContext = new HashMap<String, Object>();
    }

    public void set(String key, Object value) {
        this.testContext.put(key.toString(), value);
    }

    public Object get(String key){
        return this.testContext.get(key.toString());
    }

    public Boolean containsKey(String key){
        return this.testContext.containsKey(key.toString());
    }
}