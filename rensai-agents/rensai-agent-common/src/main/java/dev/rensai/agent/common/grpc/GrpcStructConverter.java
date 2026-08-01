package dev.rensai.agent.common.grpc;

import com.google.protobuf.ListValue;
import com.google.protobuf.NullValue;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import java.util.Collection;
import java.util.Map;

public final class GrpcStructConverter {

  private GrpcStructConverter() {}

  public static Struct toStruct(Map<String, Object> map) {
    Struct.Builder structBuilder = Struct.newBuilder();
    for (Map.Entry<String, Object> entry : map.entrySet()) {
      structBuilder.putFields(entry.getKey(), toValue(entry.getValue()));
    }
    return structBuilder.build();
  }

  private static Value toValue(Object object) {
    Value.Builder valueBuilder = Value.newBuilder();

    switch (object) {
      case null -> valueBuilder.setNullValue(NullValue.NULL_VALUE);
      case String s -> valueBuilder.setStringValue(s);
      case Number number -> valueBuilder.setNumberValue(number.doubleValue());
      case Boolean b -> valueBuilder.setBoolValue(b);
      case Map _ -> {
        @SuppressWarnings("unchecked")
        Map<String, Object> nestedMap = (Map<String, Object>) object;
        valueBuilder.setStructValue(toStruct(nestedMap));
      }
      case Collection collection -> {
        ListValue.Builder listBuilder = ListValue.newBuilder();
        for (Object item : collection) {
          listBuilder.addValues(toValue(item));
        }
        valueBuilder.setListValue(listBuilder.build());
      }
      default -> valueBuilder.setStringValue(object.toString());
    }

    return valueBuilder.build();
  }
}
