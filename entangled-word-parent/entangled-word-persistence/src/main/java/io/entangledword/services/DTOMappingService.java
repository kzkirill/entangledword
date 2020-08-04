package io.entangledword.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DTOMappingService<DTOType,EntityType> {

	private Class<EntityType> entityType;
	private Class<DTOType> dtoType;
	
	public DTOMappingService(Class<EntityType> entityType, Class<DTOType> dtoType) {
		super();
		this.entityType = entityType;
		this.dtoType = dtoType;
	}

	@Autowired
	private ModelMapper mapper;
	
	protected EntityType toEntity(DTOType dto) {
		return mapper.map(dto, entityType);
	}

	protected DTOType toDTO(EntityType entity) {
		return mapper.map(entity, dtoType);
	}


}
