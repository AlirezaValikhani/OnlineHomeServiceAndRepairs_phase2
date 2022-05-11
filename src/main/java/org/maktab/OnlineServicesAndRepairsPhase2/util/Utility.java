package org.maktab.OnlineServicesAndRepairsPhase2.util;

import org.maktab.OnlineServicesAndRepairsPhase2.entity.Offer;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Order;
import org.maktab.OnlineServicesAndRepairsPhase2.entity.Specialty;
import org.maktab.OnlineServicesAndRepairsPhase2.exceptions.*;
import org.maktab.OnlineServicesAndRepairsPhase2.service.impl.*;

import java.sql.Timestamp;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Utility {
    private final Scanner scanner = new Scanner(System.in);
    private String firstName,lastName, nationalCode,userName,password,categoryName,
            email,cityName,superCategoryName,answer,description,address,durationOfWork;
    private InvalidName invalidName;
    private InvalidNationalCode invalidNationalId;
    private InvalidPassword invalidPassword;
    private CustomerServiceImpl customerService;
    private ExpertServiceImpl expertService;
    private SpecialtyServiceImpl specialtyService;
    private OrderServiceImpl orderService;
    private OfferServiceImpl offerService;
    private Long id;
    private Integer credit;
    private Double price,bidPriceOrder,balance,bidPriceOffer;

    public Utility(InvalidName invalidName, InvalidNationalCode invalidNationalId, InvalidPassword invalidPassword, CustomerServiceImpl customerService, ExpertServiceImpl expertService, SpecialtyServiceImpl serviceService, OrderServiceImpl orderService, OfferServiceImpl offerService) {
        this.invalidName = invalidName;
        this.invalidNationalId = invalidNationalId;
        this.invalidPassword = invalidPassword;
        this.customerService = customerService;
        this.expertService = expertService;
        this.specialtyService = serviceService;
        this.orderService = orderService;
        this.offerService = offerService;
    }

    public String setFirstName(){
        while(true){
            System.out.print("Enter first name : ");
            try {
                firstName = scanner.nextLine();
                checkName(firstName);
                return firstName;
            }catch (InvalidName except){
                System.out.println(except.getMessage());
            }
        }
    }

    public String setLastName(){
        while(true){
            System.out.print("Enter last name:");
            try {
                lastName = scanner.nextLine();
                checkName(lastName);
                return lastName;
            }catch (InvalidName except){
                System.out.println(except.getMessage());
            }
        }
    }

    public String setCityName(){
        while(true){
            System.out.print("Enter city name :");
            try {
                cityName = scanner.nextLine();
                checkName(cityName);
                return cityName;
            }catch (InvalidName except){
                System.out.println(except.getMessage());
            }
        }
    }

    public String setServiceName(){
        while(true){
            System.out.print("Enter service name :");
            try {
                cityName = scanner.nextLine();
                checkName(cityName);
                return cityName;
            }catch (InvalidName except){
                System.out.println(except.getMessage());
            }
        }
    }

    public String setSuperCategoryName(){
        while(true){
            System.out.print("Enter super category name :");
            try {
                superCategoryName = scanner.nextLine();
                checkName(superCategoryName);
                return superCategoryName;
            }catch (InvalidName except){
                System.out.println(except.getMessage());
            }
        }
    }

    public String setNationalCode(){
        while(true){
            System.out.print("Enter national code:");
            try {
                nationalCode = scanner.nextLine();
                nationalCodeChecker(nationalCode);
                return nationalCode;
            }catch (InvalidNationalCode except){
                System.out.println(except.getMessage());
            }
        }
    }

    public String setPassword(){
        while(true) {
            System.out.print("Enter your username:");
            try {
                password = scanner.nextLine();
                passwordCheck(password);
                return password;
            } catch (InvalidPassword except) {
                System.out.println(except.getMessage());
            }
        }
    }

    public String setUserName(){
        while(true) {
            System.out.print("Enter your username:");
            try {
                userName = scanner.nextLine();
                passwordCheck(userName);
                return userName;
            } catch (InvalidPassword except) {
                System.out.println(except.getMessage());
            }
        }
    }

    public String setCategoryName(){
        while(true){
            System.out.print("Enter category name:");
            try {
                categoryName = scanner.nextLine();
                checkName(categoryName);
                return categoryName;
            }catch (InvalidName except){
                System.out.println(except.getMessage());
            }
        }
    }

    public String setDescription(){
        while(true){
            System.out.print("Enter your description : ");
            try {
                description = scanner.nextLine();
                return description;
            }catch (InvalidName except){
                System.out.println(except.getMessage());
            }
        }
    }

    public String setAddress(){
        while(true){
            System.out.print("Enter address : ");
            try {
                address = scanner.nextLine();
                return address;
            }catch (InvalidName except){
                System.out.println(except.getMessage());
            }
        }
    }

    public void checkName(String name){
        if(name.length() < 3 )
            throw new InvalidName("name should be more than 2 character!");
        for (Character ch:name.toCharArray()) {
            if(Character.isDigit(ch))
                throw new InvalidName("name can not have number!");
        }
    }

    public String checkAnswer(String answer){
        if(String.valueOf(answer).equals("yes") || String.valueOf(answer).equals("no"))
            return answer;
        else System.out.println("Just yes true or no!!!");
        return null;
    }

    public void nationalCodeChecker(String nationalCode){
        if(nationalCode.length() > 10 )
            throw new InvalidNationalCode("national code can't more than ten number!");
        if(nationalCode.equals(""))
            throw new InvalidNationalCode("dont enter space!");
        for (Character ch:nationalCode.toCharArray()) {
            if(!Character.isDigit(ch))
                throw new InvalidNationalCode("national code should be just number!");
        }
    }

    public void idChecker(Long id){
        if(String.valueOf(id).length() > 3 )
            throw new InvalidNationalCode("ID length can not be more than three numbers!");
        for (Character ch:String.valueOf(id).toCharArray()) {
            if(!Character.isDigit(ch))
                throw new InvalidNationalCode("ID should be just number!");
        }
    }

    public void passwordCheck(String password){
        if(password.length() < 3 )
            throw new InvalidPassword("password should be more than 2 ");
        char[] passwordArray = password.toCharArray();
        char[] signArray =  new char[] {'!','@','#','$','%','^','&','*','(',')','-','+','=','.',',','>','<','?','/','|',':',';'};
        int lowerCase = 0,upperCase = 0,sign = 0,digit = 0;
        for(int i = 0;i<passwordArray.length;i++)
            if(Character.isUpperCase(passwordArray[i]))
                ++upperCase;
        for(int i = 0;i<passwordArray.length;i++)
            if(Character.isLowerCase(passwordArray[i]))
                ++lowerCase;
        for(int i = 0;i<passwordArray.length;i++)
            if(Character.isDigit(passwordArray[i]))
                ++digit;
        for(int i=0;i<signArray.length;i++)
            for(int j=0;j<passwordArray.length;j++)
                if(signArray[i] == passwordArray[j])
                    ++sign;
        if( (lowerCase == 0) || (upperCase == 0) || (sign == 0) || (digit == 0) )
            throw new InvalidPassword("Password should have lowerCase + upperCase + sign + digit!");
    }



    public Double setBalance(){
        while(true){
            System.out.print("Balance : ");
            while (true) {
                try {
                    balance = scanner.nextDouble();
                    scanner.nextLine();
                    break;
                } catch (Exception exception) {
                    scanner.nextLine();
                    System.out.print("Just number please : ");
                }
            }
            if(balance < 0 ){
                System.out.println("You must enter a price more than zero!");
            }else
                break;
        }
        return balance;
    }

    public Double setBasePrice(){
        while(true){
            System.out.print("Base price : ");
            while (true) {
                try {
                    balance = scanner.nextDouble();
                    scanner.nextLine();
                    break;
                } catch (Exception exception) {
                    scanner.nextLine();
                    System.out.print("Just number please : ");
                }
            }
            if(balance < 0 ){
                System.out.println("You must enter a price more than zero!");
            }else
                break;
        }
        return balance;
    }

    public Integer setCredit(){
            System.out.print("Credit : ");
            while (true) {
                try {
                    credit = scanner.nextInt();
                    scanner.nextLine();
                    break;
                } catch (Exception exception) {
                    scanner.nextLine();
                    System.out.print("Just number please : ");
                }
            }
        return credit;
    }

    public String setEmail() {
        while (true) {
            String email = regexAdder("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$", "Email", "Example: alirezaVk@gmail.com");
            if (customerService.findByEmailAddress(email) != null || expertService.findByEmailAddress(email) != null) {
                System.out.println("Email address already exists!!!");
            } else return email;
        }
    }

    public String regexAdder(String regex, String tag, String additionalInfo) {
        while (true) {
            System.out.print(tag + "(" + additionalInfo + "): ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (Pattern.compile(regex).matcher(input).matches()) {
                return input;
            } else {
                System.out.println("Wrong Email Format!!!");
            }
        }
    }

    public String setAnswer(){
        while(true){
            System.out.print("Is this person approved by you?(Enter yes or no) : ");
            try {
                answer = scanner.nextLine();
                checkAnswer(answer);
                break;
            }catch (InvalidAnswer except){
                System.out.println(except.getMessage());
            }
        }
        return answer;
    }

    public Long setId(){
        while(true){
            System.out.print("Enter id : ");
            try {
                id = scanner.nextLong();
                scanner.nextLine();
                return id;
            }catch (InputMismatchException except){
                scanner.nextLine();
                System.out.println("You just have to enter the number");
            }
        }
    }

    public Timestamp orderExecutionDate(){
        while (true) {
            System.out.print("Enter execution date (like this -> 2022-10-02 18:48:00) : ");
            String text = scanner.nextLine();
            try {
                return Timestamp.valueOf(text);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Timestamp startTime(){
        while (true) {
            System.out.print("Enter start time date (like this -> 2022-10-02 18:48:00) : ");
            String text = scanner.nextLine();
            try {
                return Timestamp.valueOf(text);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String setImagePath(){
        while (true){
            System.out.print("Image path : ");
            try {
                String imagePath = scanner.nextLine();
                String[] returnedName = imagePath.split("\\.");
                String format = returnedName[returnedName.length - 1];
                if (format.equals("png"))
                    return imagePath;
            } catch (Exception e) {
                throw new InvalidImageFormat();
            }
        }
    }

    public Specialty serviceExistence(){
        while (true) {
            try {
                Long specialtyId = setId();
                Specialty service = specialtyService.getById(specialtyId);
                if (service != null){
                    return service;
                }
            } catch (Exception e) {
                System.out.println("This specialty doesn't exists!!!");
                break;
            }
        }
        return null;
    }

    public Offer offerExistence(){
        while (true) {
            try {
                Long offerId = setId();
                Offer offer = offerService.getById(offerId);
                if (offer != null){
                    return offer;
                }
            } catch (Exception e) {
                System.out.println("This offer doesn't exists!!!");
                break;
            }
        }
        return null;
    }

    public Double setBidPriceOrder(){
        while(true){
            System.out.print("Bid price order : ");
            while (true) {
                try {
                    bidPriceOrder = scanner.nextDouble();
                    scanner.nextLine();
                    break;
                } catch (Exception exception) {
                    scanner.nextLine();
                    System.out.print("Just number please : ");
                }
            }
            if(bidPriceOrder < 0 ){
                System.out.println("You must enter a price more than zero!");
            }else
                break;
        }
        return bidPriceOrder;
    }

    public Double setBidPriceOffer(){
        while(true){
            System.out.print("Bid price offer : ");
            while (true) {
                try {
                    bidPriceOffer = scanner.nextDouble();
                    scanner.nextLine();
                    break;
                } catch (Exception exception) {
                    scanner.nextLine();
                    System.out.print("Just number please : ");
                }
            }
            if(bidPriceOffer < 0 ){
                System.out.println("You must enter a price more than zero!");
            }else
                break;
        }
        return bidPriceOffer;
    }

    public Order orderExistence(){
        while (true) {
            try {
                Long id = setId();
                Order order = orderService.getById(id);
                if (order != null){
                    return order;
                }
            } catch (Exception e) {
                System.out.println("This order doesn't exists!!!");
                break;
            }
        }
        return null;
    }

    public String setDurationOfWork(){
        while(true){
            System.out.print("Enter duration of work : ");
            try {
                durationOfWork = scanner.nextLine();
                return durationOfWork;
            }catch (InvalidName except){
                System.out.println(except.getMessage());
            }
        }
    }
}
