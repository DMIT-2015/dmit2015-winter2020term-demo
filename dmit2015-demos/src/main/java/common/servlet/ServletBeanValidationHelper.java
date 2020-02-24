package common.servlet;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * A helper class for use with Servlets to simply the task of retrieving parameter values, converting the parameter values to the required data types,
 * and setting a data object with the converted values.
 * 
 * @author Sam Wu
 * @verson 2020.02.18
 */
public class ServletBeanValidationHelper {

	private Logger logger = Logger.getLogger(ServletBeanValidationHelper.class.getSimpleName());
	
	/**
	 * Return an JsonArray with collection validation errors for each parameter of the request
	 * 
	 * @param <T> generic method that accepts any type of POJO class
	 * @param request the HttpServletRequest with the parameters to validate
	 * @param classType the POJO class to validate
	 * @param typeInstance the POJO object to copy the parameter values to
	 * @return an JsonArray with a collection of validation errors for each parameter name and its validation error message
	 */
	public <T> JsonArray validateRequestParameters(HttpServletRequest request, Class<T> classType, T typeInstance) {
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		
		// Check the number of parameters of the request
		if (request.getParameterMap().size() > 0) {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			
			// Get a Map of the parameters of the request
			Map<String, String[]> requestMap = request.getParameterMap();
			// Get all the fields of the classType
			Field[] fields = classType.getDeclaredFields();
			
			// Process each parameter of the request
			requestMap.entrySet().forEach(parameter -> {
				
				// Check if the parameter name matches a field of the classType
				boolean isFieldName = Arrays.stream(fields)
					.anyMatch(currentField -> currentField.getName().equals(parameter.getKey()));
				
				if (isFieldName) {
					// Get the field for the matching parameter name of the classType
					Field firstField = Arrays.stream(fields)
						.filter(currentField -> currentField.getName().equals(parameter.getKey()))
						.findFirst()
						.get();
					
					// Get the parameter value
					String stringValue;
					if (parameter.getValue().length == 1) {
						stringValue = parameter.getValue()[0];
					} else {
						stringValue = Arrays.asList( parameter.getValue() ).stream().collect(Collectors.joining(","));
					}

					// Convert the stringValue to the required data type and set the correspond field value of the typeInstance
					switch (firstField.getType().getTypeName()) {
					
						case "java.math.BigDecimal": {
							// Convert parameter value from a String to a BigDecimal
							BigDecimal bigDecimalValue = null;
							if (NumberUtils.isParsable(stringValue)) {
								try {
									bigDecimalValue = new BigDecimal(stringValue);
								} catch (NumberFormatException ex) {
									JsonObject exceptionJsonObject = Json.createObjectBuilder()
										.add(parameter.getKey(), ex.getMessage())
										.build();
									jsonArrayBuilder.add(exceptionJsonObject);
				
									logger.info("Exception: " + ExceptionUtils.getStackTrace(ex));
								}	
							}
							// Set the data field of the typeInstance with the parameter value
							try {
								String methodName = "set" + parameter.getKey().substring(0, 1).toUpperCase() + parameter.getKey().substring(1);
								Method setValueMethod = classType.getDeclaredMethod(methodName, BigDecimal.class);
								setValueMethod.invoke(typeInstance, bigDecimalValue);
							} catch (Exception ex) {
								JsonObject exceptionJsonObject = Json.createObjectBuilder()
									.add(parameter.getKey(), ex.getMessage())
									.build();
								jsonArrayBuilder.add(exceptionJsonObject);
			
								logger.info("Exception: " + ExceptionUtils.getStackTrace(ex));
							}								
						}				
							break;
							
						case "int": {
							// Convert parameter value from a String to a int
							int intValue = 0;
							if (NumberUtils.isParsable(stringValue)) {
								try {
									intValue = Integer.parseInt(stringValue);
								} catch (NumberFormatException ex) {
									JsonObject exceptionJsonObject = Json.createObjectBuilder()
										.add(parameter.getKey(), ex.getMessage())
										.build();
									jsonArrayBuilder.add(exceptionJsonObject);
				
									logger.info("Exception: " + ExceptionUtils.getStackTrace(ex));
								}	
							}
							
							try {
								String methodName = "set" + parameter.getKey().substring(0, 1).toUpperCase() + parameter.getKey().substring(1);
								Method setValueMethod = classType.getDeclaredMethod(methodName, int.class);
								setValueMethod.invoke(typeInstance, intValue);
							} catch (Exception ex) {
								JsonObject exceptionJsonObject = Json.createObjectBuilder()
									.add(parameter.getKey(), ex.getMessage())
									.build();
								jsonArrayBuilder.add(exceptionJsonObject);
			
								logger.info("Exception: " + ExceptionUtils.getStackTrace(ex));
							}								
						}						
							break;

						case "java.lang.Integer": {
							// Convert parameter value from a String to a Integer
							Integer integerValue = null;
							if (NumberUtils.isParsable(stringValue)) {
								try {
									integerValue = Integer.valueOf(stringValue);
								} catch (NumberFormatException ex) {
									JsonObject exceptionJsonObject = Json.createObjectBuilder()
										.add(parameter.getKey(), ex.getMessage())
										.build();
									jsonArrayBuilder.add(exceptionJsonObject);
				
									logger.info("Exception: " + ExceptionUtils.getStackTrace(ex));
								}	
							}
							
							try {
								String methodName = "set" + parameter.getKey().substring(0, 1).toUpperCase() + parameter.getKey().substring(1);
								Method setValueMethod = classType.getDeclaredMethod(methodName, Integer.class);
								setValueMethod.invoke(typeInstance, integerValue);
							} catch (Exception ex) {
								JsonObject exceptionJsonObject = Json.createObjectBuilder()
									.add(parameter.getKey(), ex.getMessage())
									.build();
								jsonArrayBuilder.add(exceptionJsonObject);
			
								logger.info("Exception: " + ExceptionUtils.getStackTrace(ex));
							}								
						}						
							break;

						case "java.lang.Long": {
							// Convert parameter value from a String to a Long
							Long longValue = null;
							if (NumberUtils.isParsable(stringValue)) {
								try {
									longValue = Long.valueOf(stringValue);
								} catch (NumberFormatException ex) {
									JsonObject exceptionJsonObject = Json.createObjectBuilder()
										.add(parameter.getKey(), ex.getMessage())
										.build();
									jsonArrayBuilder.add(exceptionJsonObject);
				
									logger.info("Exception: " + ExceptionUtils.getStackTrace(ex));
								}	
							}
							
							try {
								String methodName = "set" + parameter.getKey().substring(0, 1).toUpperCase() + parameter.getKey().substring(1);
								Method setValueMethod = classType.getDeclaredMethod(methodName, Long.class);
								setValueMethod.invoke(typeInstance, longValue);
							} catch (Exception ex) {
								JsonObject exceptionJsonObject = Json.createObjectBuilder()
									.add(parameter.getKey(), ex.getMessage())
									.build();
								jsonArrayBuilder.add(exceptionJsonObject);
			
								logger.info("Exception: " + ExceptionUtils.getStackTrace(ex));
							}								
						}						
							break;

						case "double": {
							// Convert parameter value from a String to a double
							double doubleValue = 0;
							if (NumberUtils.isParsable(stringValue)) {
								try {
									doubleValue = Double.parseDouble(stringValue);
								} catch (NumberFormatException ex) {
									JsonObject exceptionJsonObject = Json.createObjectBuilder()
										.add(parameter.getKey(), ex.getMessage())
										.build();
									jsonArrayBuilder.add(exceptionJsonObject);
				
									logger.info("Exception: " + ExceptionUtils.getStackTrace(ex));
								}	
							}
							
							try {
								String methodName = "set" + parameter.getKey().substring(0, 1).toUpperCase() + parameter.getKey().substring(1);
								Method setValueMethod = classType.getDeclaredMethod(methodName, double.class);
								setValueMethod.invoke(typeInstance, doubleValue);
							} catch (Exception ex) {
								JsonObject exceptionJsonObject = Json.createObjectBuilder()
									.add(parameter.getKey(), ex.getMessage())
									.build();
								jsonArrayBuilder.add(exceptionJsonObject);
			
								logger.info("Exception: " + ExceptionUtils.getStackTrace(ex));
							}								
						}						
							break;
							
						case "java.lang.Double": {
							// Convert parameter value from a String to a Double
							Double doubleValue = null;
							if (NumberUtils.isParsable(stringValue)) {
								try {
									doubleValue = Double.valueOf(stringValue);
								} catch (NumberFormatException ex) {
									JsonObject exceptionJsonObject = Json.createObjectBuilder()
										.add(parameter.getKey(), ex.getMessage())
										.build();
									jsonArrayBuilder.add(exceptionJsonObject);
				
									logger.info("Exception: " + ExceptionUtils.getStackTrace(ex));
								}	
							}
							
							try {
								String methodName = "set" + parameter.getKey().substring(0, 1).toUpperCase() + parameter.getKey().substring(1);
								Method setValueMethod = classType.getDeclaredMethod(methodName, Double.class);
								setValueMethod.invoke(typeInstance, doubleValue);
							} catch (Exception ex) {
								JsonObject exceptionJsonObject = Json.createObjectBuilder()
									.add(parameter.getKey(), ex.getMessage())
									.build();
								jsonArrayBuilder.add(exceptionJsonObject);
			
								logger.info("Exception: " + ExceptionUtils.getStackTrace(ex));
							}								
						}						
							break;
														
						case "java.time.LocalDate": {
							// Convert parameter value from a String to a LocalDate
							try {
								LocalDate localDateValue = LocalDate.parse(stringValue);
								String methodName = "set" + parameter.getKey().substring(0, 1).toUpperCase() + parameter.getKey().substring(1);
								Method setValueMethod = classType.getDeclaredMethod(methodName, LocalDate.class);
								setValueMethod.invoke(typeInstance, localDateValue);
							} catch (Exception ex) {
								JsonObject exceptionJsonObject = Json.createObjectBuilder()
									.add(parameter.getKey(), ex.getMessage())
									.build();
								jsonArrayBuilder.add(exceptionJsonObject);
								
								logger.info("Exception: " + ExceptionUtils.getStackTrace(ex));
							}
						}
							break;
							
						case "java.lang.String":
							// Set the typeInstance field to the parameter value
							String methodName = "set" + parameter.getKey().substring(0, 1).toUpperCase() + parameter.getKey().substring(1);
							try {
								Method setValueMethod = classType.getDeclaredMethod(methodName, String.class);
								setValueMethod.invoke(typeInstance, stringValue);
							} catch (Exception ex) {
								JsonObject exceptionJsonObject = Json.createObjectBuilder()
									.add(parameter.getKey(), ex.getMessage())
									.build();
								jsonArrayBuilder.add(exceptionJsonObject);
								
								logger.info("Exception: " + ExceptionUtils.getStackTrace(ex));
							}
							break;	
					}
					
				}
			});
			
			// Check for constraint violations
			Set<ConstraintViolation<T>> constraintViolations = validator.validate(typeInstance);
			// For each ConstraintViolation create a JsonObject with field name and validation error message and add it to jsonArrayArrayBuilder
			constraintViolations.stream().forEach(singleConstraintViolation -> {
				JsonObject constraintJsonObject = Json.createObjectBuilder()
					.add(singleConstraintViolation.getPropertyPath().toString(), singleConstraintViolation.getMessage())
					.build();
					jsonArrayBuilder.add(constraintJsonObject);
				});
		}
		
		return jsonArrayBuilder.build();
	}
}
