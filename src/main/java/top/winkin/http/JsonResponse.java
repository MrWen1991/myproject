package top.winkin.http;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang.StringUtils;
import top.winkin.util.JsonUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonResponse implements Serializable {

    private int code = 0;
    private boolean success = true;
    private String message;
    @Deprecated
    private List<String> messages = new ArrayList<String>();
    private Map<String, Object> data = new HashMap<String, Object>();

    public JsonResponse() {
    }

    public JsonResponse(boolean success) {
        this(success, null);
    }

    public JsonResponse(String message) {
        this(false, message);
    }

    public JsonResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public JsonResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public JsonResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getCode() {
        return code;
    }

    public JsonResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public JsonResponse appendMessage(String msg) {
        if (StringUtils.isBlank(message)) {
            message = msg;
        }
        messages.add(msg);
        return this;
    }

    public List<String> getMessages() {
        return messages;
    }

    public JsonResponse set(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public Object get(String key) {
        return data.get(key);
    }

    public Map<String, Object> getData() {
        return data;
    }

    public JsonResponse setData(Map<String, Object> data) {
        return setData(data, true);
    }

    public JsonResponse setData(Map<String, Object> data, boolean keepOldMap) {
        if (data == null) {
            return this;
        }
        if (keepOldMap) {
            this.data.putAll(data);
        } else {
            this.data = data;
        }
        return this;
    }

    public JsonResponse setEntityData(Object entity) {
        return setEntityData(entity, true);
    }

    public JsonResponse setEntityData(Object entity, boolean keepOldMap) {
        if (entity instanceof Boolean || entity instanceof Number || entity instanceof CharSequence || entity instanceof List || entity.getClass().isArray() || entity.getClass().isPrimitive()) {
            throw new RuntimeException("Boolean/Number/CharSequence/List/Array及基本数据类型的数据:" + entity + ",不能设置为data字段的值。请改用set(key,value)方法");
        }
        if (entity instanceof Map && ((Map) entity).isEmpty()) {
            return this;
        }
        //TODO 如果类型是Map，且所有key的类型都是String,则调用setData即可。但是对于没有指定key类型的map需循环判断，成本似乎有所不值。
        String json = JsonUtil.dump(entity);
        if (StringUtils.isBlank(json) || json.length() < 5) {
            throw new RuntimeException("无法将值\"" + entity + "\"转为Map类型，请改用set(key,value)方法");
        }
        Map entityData = JsonUtil.load(json);
        return setData(entityData, keepOldMap);
    }

    public static void main(String[] args) {
        JsonUtil.load("{}");
//        new JsonResponse().setEntityData(83787594);
//        new JsonResponse().setEntityData(new String[]{});
        new JsonResponse().setEntityData("");
    }

    @Override
    public String toString() {
        return "JsonResponse{" +
                "code=" + code +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", messages=" + messages +
                ", data=" + data +
                '}';
    }
}
