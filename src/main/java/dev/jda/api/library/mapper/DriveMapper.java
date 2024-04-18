//package dev.jda.apilibary.mapper;
//
//import dev.jda.apilibary.entity.Drive;
//import dev.jda.modellibary.DriveDTO;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class DriveMapper implements Mapper {
//
//    @Override
//    public void createMapper(ModelMapper mm) {
//        mm.createTypeMap(Drive.class, DriveDTO.class).addMappings(mapper -> mapper.skip(DriveDTO::setName)).setPostConverter(mappingContext -> {
//            Drive drive = mappingContext.getSource();
//            DriveDTO driveDTO = mappingContext.getDestination();
//            driveDTO.setName(drive.getName());
//            return driveDTO;
//        });
//    }
//}
