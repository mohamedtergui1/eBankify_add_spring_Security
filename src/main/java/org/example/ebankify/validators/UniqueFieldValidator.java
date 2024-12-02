package org.example.ebankify.validators;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.ebankify.annotation.UniqueField;

public class UniqueFieldValidator implements ConstraintValidator<UniqueField, String> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> entity;
    private String field;

    @Override
    public void initialize(UniqueField annotation) {
        this.entity = annotation.entity();
        this.field = annotation.field();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }



        try {
            String queryStr = String.format("SELECT COUNT(e) FROM %s e WHERE e.%s = :value",
                    entity.getSimpleName(), field);

            Long count = entityManager.createQuery(queryStr, Long.class)
                    .setParameter("value", value)
                    .getSingleResult();

            // If count == 0, the value is unique
            return count == 0;
        } catch (Exception e) {
            throw new RuntimeException("Error validating unique field: " + field + " for entity " + entity.getSimpleName(), e);
        }
    }
}
