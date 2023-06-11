package com.dat.csmis;


	import org.junit.jupiter.api.Assertions;
	import org.junit.jupiter.api.Test;
    import org.mockito.Mock;
    import com.dat.csmis.service.EmployeeNotFoundException;

	public class EmployeeNotFoundExceptionTest {
		
		@Mock
		private EmployeeNotFoundException empNotFoundException;

	    @Test
	    public void testEmployeeNotFoundException() {
	        String errorMessage = "Employee not found";
	        EmployeeNotFoundException exception = new EmployeeNotFoundException(errorMessage);

	        Assertions.assertEquals(errorMessage, exception.getMessage());
	    }
	}
	
	
////////////////////////////////////// J unit Finished //////////////////////////////////////



