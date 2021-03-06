package com.assignment;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.assignment");

        noClasses()
            .that()
            .resideInAnyPackage("com.assignment.service..")
            .or()
            .resideInAnyPackage("com.assignment.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.assignment.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
