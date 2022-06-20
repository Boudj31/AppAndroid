package fr.dawan.back.dto;

import org.modelmapper.ModelMapper;

public class DtoTools {

	private static ModelMapper mapper = new ModelMapper();
	public static <Source, Destination> Destination convert(Source obj, Class<Destination> clazz ) {
		return mapper.map(obj, clazz);
	}
}
