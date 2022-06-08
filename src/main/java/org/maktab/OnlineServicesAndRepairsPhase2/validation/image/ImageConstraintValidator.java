package org.maktab.OnlineServicesAndRepairsPhase2.validation.image;

import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.BadFileTypeException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.EmptyFileException;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.WrongFileFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;

public class ImageConstraintValidator implements ConstraintValidator<Image, MultipartFile> {
    @Override
    public void initialize(Image constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if(multipartFile.isEmpty())
            throw new EmptyFileException();
         String[] array = multipartFile.getContentType().split("/");
         if(!array[0].equals("image"))
             throw new BadFileTypeException();
         if((!array[1].equals("jpg")) && (!array[1].equals("jpeg")))
             throw new WrongFileFormat();
         try {
             if (multipartFile.getBytes().length > 300000)
                 return false;
         }catch (IOException e){
             e.printStackTrace();
         }
         return true;
    }
}
