package io.entangledword.port.out;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class DTOMappingService<T,R> {

	private Class<T> dtoType;
	private Class<R> entityType;
	
	public DTOMappingService(Class<T> dtoType,Class<R> entityType) {
		super();
		this.dtoType = dtoType;
		this.entityType = entityType;
	}

	@Autowired
	private ModelMapper mapper;
	
	public R toEntity(T dto) {
		return mapper.map(dto, entityType);
	}

	public T toDTO(R entity) {
		return mapper.map(entity, dtoType);
	}


}
