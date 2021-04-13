package tests;

import bussiness_objects.Student;
import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.*;

public class StudentRegistrationFormData {
    private static final Faker faker = new Faker();

    private static final Map<String, String[]> statesAndCities = new HashMap<String, String[]>() {{
        put("NCR", new String[]{"Delhi", "Gurgaon", "Noida"});
        put("Uttar Pradesh", new String[]{"Agra", "Lucknow", "Merrut"});
        put("Haryana", new String[]{"Karnal", "Panipat"});
        put("Rajasthan", new String[]{"Jaipur", "Jaiselmer"});
    }};

    public static Student generateStudent() {
        Student student = new Student();

        student.setFirstName(faker.name().firstName());
        student.setLastName(faker.name().lastName());
        student.setEmail(faker.internet().emailAddress());
        student.setGender(getRandomGender());
        student.setMobileNumber(faker.number().digits(10));
        Calendar dateOfBirth = Calendar.getInstance();
        dateOfBirth.setTime(faker.date().birthday());
        student.setDateOfBirth(dateOfBirth);
        student.setSubjects(getRandomSubjects());
        student.setHobbies(getRandomHobbies());
        student.setPicture("picture.png");
        student.setAddress(faker.address().streetAddress(true));
        String state = getRandomState();
        student.setState(state);
        student.setCity(getRandomCity(state));

        return student;
    }

    public static Map<String, String> generateExpectedData(Student student) {
        Map<String, String> expectedData = new HashMap<>();
        expectedData.put("Student Name", student.getFirstName() + " " + student.getLastName());
        expectedData.put("Student Email", student.getEmail());
        expectedData.put("Gender", student.getGender());
        expectedData.put("Mobile", student.getMobileNumber());
        expectedData.put("Date of Birth", new SimpleDateFormat("dd MMMM,yyyy", Locale.ENGLISH)
                .format(student.getDateOfBirth().getTime()));
        expectedData.put("Subjects", String.join(", ", student.getSubjects()));
        expectedData.put("Hobbies", String.join(", ", student.getHobbies()));
        expectedData.put("Picture", student.getPicture());
        expectedData.put("Address", student.getAddress());
        expectedData.put("State and City", student.getState() + " " + student.getCity());
        return expectedData;
    }

    private static String getRandomGender() {
        String[] words = {"Male", "Female", "Other"};
        int orderNumber = faker.random().nextInt(words.length);

        return words[orderNumber];
    }

    private static String getRandomState() {
        List<String> states = new ArrayList<>(statesAndCities.keySet());
        int orderNumber = faker.random().nextInt(states.size());

        return states.get(orderNumber);
    }

    private static String getRandomCity(String state) {
        String[] cities = statesAndCities.get(state);
        int orderNumber = faker.random().nextInt(cities.length);

        return cities[orderNumber];
    }

    private static List<String> getRandomSubjects() {
        List<String> subjects = new ArrayList<>(Arrays.asList("Accounting", "Arts", "Biology", "Chemistry", "Commerce", "Maths"));
        List<String> randomSubjects = new ArrayList<>();
        int countSubjects = faker.random().nextInt(subjects.size());
        for (int i = 0; i < countSubjects; i++) {
            int orderNumber = faker.random().nextInt(subjects.size());
            String subject = subjects.get(orderNumber);
            randomSubjects.add(subject);
            subjects.remove(subject);
        }
        return randomSubjects;
    }

    private static List<String> getRandomHobbies() {
        List<String> hobbies = new ArrayList<>(Arrays.asList("Sports", "Reading", "Music"));
        List<String> randomHobbies = new ArrayList<>();
        int countHobbies = faker.random().nextInt(hobbies.size());
        for (int i = 0; i < countHobbies; i++) {
            int orderNumber = faker.random().nextInt(hobbies.size());
            String subject = hobbies.get(orderNumber);
            randomHobbies.add(subject);
            hobbies.remove(subject);
        }
        return randomHobbies;
    }
}
