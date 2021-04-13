package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.Calendar;
import java.util.List;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class StudentRegistrationFormPage {
    private final SelenideElement firstNameInput = $("#firstName"),
            lastNameInput = $("#lastName"),
            emailInput = $("#userEmail"),
            genderOption = $("#genterWrapper"),
            mobileNumberInput = $("#userNumber"),
            dateOfBirthInput = $("#dateOfBirthInput"),
            monthOfBirthOption = $(".react-datepicker__month-select"),
            yearOfBirthOption = $(".react-datepicker__year-select"),
            subjectsInput = $("#subjectsInput"),
            hobbiesCheckboxes = $("#hobbiesWrapper"),
            uploadPictureButton = $("#uploadPicture"),
            currentAddressTextarea = $("#currentAddress"),
            stateOption = $("#state"),
            cityOption = $("#city"),
            submitButton = $("#submit"),
            popup = $("#example-modal-sizes-title-lg");

    private final String dayCalendarXPathPattern = "//div[contains(@class, 'datepicker__day') and (text()='%d') and " +
            "not (contains(@class, 'day--outside-month'))]";
    private final String tableRowXPathPattern = "//td[text()='%s']/following-sibling::td";

    @Step("Set first name '{value}'")
    public StudentRegistrationFormPage setFirstName(String value) {
        firstNameInput.setValue(value);
        return this;
    }

    @Step("Set last name '{value}'")
    public StudentRegistrationFormPage setLastName(String value) {
        lastNameInput.setValue(value);
        return this;
    }

    @Step("Set email '{value}'")
    public StudentRegistrationFormPage setEmail(String value) {
        emailInput.setValue(value);
        return this;
    }

    @Step("Set gender '{value}'")
    public StudentRegistrationFormPage setGender(String value) {
        genderOption.find(byText(value)).click();
        return this;
    }

    @Step("Set mobile number '{value}'")
    public StudentRegistrationFormPage setMobileNumber(String value) {
        mobileNumberInput.setValue(value);
        return this;
    }

    @Step("Set birth date")
    public StudentRegistrationFormPage setBirthDate(Calendar date) {
        dateOfBirthInput.click();
        monthOfBirthOption.selectOptionByValue(String.valueOf(date.get(Calendar.MONTH)));
        yearOfBirthOption.selectOptionByValue(String.valueOf(date.get(Calendar.YEAR)));
        $x(String.format(dayCalendarXPathPattern, date.get(Calendar.DAY_OF_MONTH))).click();
        return this;
    }

    @Step("Set subjects '{values}'")
    public StudentRegistrationFormPage setSubjects(List<String> values) {
        values.forEach(value -> subjectsInput.setValue(value).pressEnter());
        return this;
    }

    @Step("Set hobbies '{values}'")
    public StudentRegistrationFormPage setHobbies(List<String> values) {
        values.forEach(value -> hobbiesCheckboxes.find(byText(value)).click());
        return this;
    }

    @Step("Upload picture '{path}'")
    public StudentRegistrationFormPage uploadPicture(String path) {
        uploadPictureButton.uploadFromClasspath(path);
        return this;
    }

    @Step("Set current address '{value}'")
    public StudentRegistrationFormPage setCurrentAddress(String value) {
        currentAddressTextarea.setValue(value);
        return this;
    }

    @Step("Set state '{value}'")
    public StudentRegistrationFormPage setState(String value) {
        stateOption.scrollTo().click();
        stateOption.$("div[class*='menu']").find(byText(value)).click();
        return this;
    }

    @Step("Set city '{value}'")
    public StudentRegistrationFormPage setCity(String value) {
        cityOption.click();
        cityOption.$("div[class*='menu']").find(byText(value)).click();
        return this;
    }

    @Step("Submit form")
    public StudentRegistrationFormPage submit() {
        submitButton.click();
        return this;
    }

    @Step("Popup with result should appear")
    public void popupShouldAppear() {
        popup.should(appear);
    }

    @Step("Get value '{row}'")
    public String getValueInTableRow(String row) {
        return $x(String.format(tableRowXPathPattern, row)).text();
    }

}