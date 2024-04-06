//package dev.jda.demoapilibary.mapper;
//
//import dev.jda.demoapilibary.entity.Demo;
//import dev.jda.demomodellibary.DemoDTO;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class DemoMapper implements Mapper {
//
//    @Override
//    public void createMapper(ModelMapper mm) {
//        mm.createTypeMap(Demo.class, DemoDTO.class).addMappings(mapper -> mapper.skip(DemoDTO::setName)).setPostConverter(mappingContext -> {
//            Demo demo = mappingContext.getSource();
//            DemoDTO userDTO = mappingContext.getDestination();
//            userDTO.setName(demo.getName());
//            return userDTO;
//        });
//    }
//}
