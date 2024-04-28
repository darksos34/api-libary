//package dev.jda.apilibary.mapper;
//
//import dev.jda.apilibary.entity.User;
//import dev.jda.modellibary.UserDTO;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class UserMapper implements Mapper {
//
//    @Override
//    public void createMapper(ModelMapper mm) {
//        mm.createTypeMap(User.class, UserDTO.class).addMappings(mapper -> mapper.skip(UserDTO::setName)).setPostConverter(mappingContext -> {
//            User user = mappingContext.getSource();
//            UserDTO userDTO = mappingContext.getDestination();
//            userDTO.setName(user.getName());
//            return userDTO;
//        });
//    }
//}
