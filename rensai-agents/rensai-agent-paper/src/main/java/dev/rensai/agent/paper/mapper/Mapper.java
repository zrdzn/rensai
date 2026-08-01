package dev.rensai.agent.paper.mapper;

import java.util.Map;

public interface Mapper<T> {
  Map<String, Object> map(T model);
}
