package de.tudarmstadt.linglit.linfw.app.features;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.ImmutableMap;

import de.tudarmstadt.linglit.linfw.app.annotation.FeatureVector;

public class DefaultFeatureGenerator implements FeatureGenerator<Object> {
	private static final DefaultFeatureGenerator instance = new DefaultFeatureGenerator();
	
	public static DefaultFeatureGenerator instance() {
		return instance;
	}
	
	public FeatureVector generate(String namespace, Object object, Set<Object> visited) {
		if(visited.contains(object)) return FeatureVector.empty();
		final String prefix = namespace.isEmpty() ? "" : namespace+"#";
		
		if(object==null)
			return FeatureVector.fromMap(ImmutableMap.of((Object)namespace, Double.valueOf(0.0)));
		else if(object instanceof Number)
			return FeatureVector.fromMap(ImmutableMap.of((Object)namespace, Double.valueOf(((Number)object).doubleValue())));
		else if(object instanceof Boolean)
			return FeatureVector.fromMap(ImmutableMap.of((Object)namespace, Double.valueOf(((Boolean)object).booleanValue() ? 1.0 : 0.0)));
		else if(object instanceof Iterable) {
			int i=0;
			FeatureVector featureVector = FeatureVector.empty();
			for(Object o : (Iterable<?>)object) {
				featureVector = featureVector.plus(generate(prefix+i, o));
				i++;
			}
			return featureVector;
		} else if(object instanceof Map) {
			FeatureVector featureVector = FeatureVector.empty();
			for(Entry<Object, Object> entry : ((Map<Object,Object>)object).entrySet())
				featureVector = featureVector.plus(generate(prefix+entry.getKey(),entry.getValue()));
			return featureVector;
		} else if(object instanceof CharSequence)
			return FeatureVector.fromMap(ImmutableMap.of((Object)(prefix+object.hashCode()), Double.valueOf(1.0)));
		else if(object instanceof Enum)
			return FeatureVector.fromMap(ImmutableMap.of((Object)(prefix+((Enum)object).name()), Double.valueOf(1.0)));

		FeatureVector featureVector = FeatureVector.empty();
		Class<?> type = object.getClass();
		Field[] fields = type.getDeclaredFields();
		for(Field field : fields) {
			if(!visited.contains(field)) {
				visited.add(object);
				try {
					field.setAccessible(true);
					featureVector = featureVector.plus(generate(prefix+field.getName(), field.get(object),visited));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return featureVector;
	}
	
	@Override
	public FeatureVector generate(String namespace, Object object) {
		return generate(namespace, object, new HashSet<>());
	}

	@Override
	public Class<Object> type() {
		return Object.class;
	}

	private DefaultFeatureGenerator() {}
}
