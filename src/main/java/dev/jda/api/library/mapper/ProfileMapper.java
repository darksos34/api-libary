package dev.jda.api.library.mapper;

import dev.jda.api.library.entity.Profile;
import dev.jda.model.library.ProfileDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import static org.modelmapper.Conditions.isNotNull;

@Component
public class ProfileMapper extends BaseMapper implements Mapper{



    @Override
    public void createMapper(ModelMapper mm) {
        mm.createTypeMap(Profile.class, ProfileDTO.class)
                .addMappings(mapper -> {
                    mapper.when(isNotNull()).using(Profile::getUuid, ProfileDTO::getUuid);
                    mapper.map(Profile::getName, ProfileDTO::getName);
                    mapper.map(Profile::getCode, ProfileDTO::getCode);
                });
    }

}
