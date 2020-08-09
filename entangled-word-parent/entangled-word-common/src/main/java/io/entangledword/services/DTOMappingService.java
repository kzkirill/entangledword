package io.entangledword.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class DTOMappingService<T,R> {

	private Class<R> entityType;
	private Class<T> dtoType;
	
	public DTOMappingService(Class<R> entityType, Class<T> dtoType) {
		super();
		this.entityType = entityType;
		this.dtoType = dtoType;
	}

	@Autowired
	private ModelMapper mapper;
	
	protected R toEntity(T dto) {
		return mapper.map(dto, entityType);
	}

	protected T toDTO(R entity) {
		return mapper.map(entity, dtoType);
	}


}
