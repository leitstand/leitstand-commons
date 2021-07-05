package io.leitstand.commons.model;

import static io.leitstand.commons.UniqueKeyConstraintViolationException.key;
import static io.leitstand.commons.model.RollbackExceptionUtil.givenRollbackException;
import static io.leitstand.commons.rs.ReasonCode.VAL0002E_INVALID_VALUE;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.function.Supplier;

import javax.transaction.RollbackException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import io.leitstand.commons.EntityNotFoundException;
import io.leitstand.commons.UniqueKeyConstraintViolationException;

public class RollbackExceptionUtilTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	
	@Test
	public void ignore_non_rollback_exception() {
		Supplier<?> s = mock(Supplier.class);
		givenRollbackException(new Exception())
		.whenEntityExists(s)
		.thenThrow(new UniqueKeyConstraintViolationException(VAL0002E_INVALID_VALUE,  // Dummy reason code.
															 key("unit_test", "dummy")));

		
		verifyZeroInteractions(s);
		
		
	}
	
	@Test
	public void do_not_throw_unique_key_constraint_violation_exception_if_entity_does_not_exist() {
		try {
	    givenRollbackException(new RollbackException())
		.whenEntityExists(() -> null)
		.thenThrow(new UniqueKeyConstraintViolationException(VAL0002E_INVALID_VALUE, // Dummy reason code.
															 key("unit_test", "dummy")));
		} catch (Exception e) {
		    fail("Unexpected exception: "+e);
		}
	}
	
	
	@Test
	public void do_not_throw_unique_key_constraint_violation_exception_if_test_throws_EntityNotFoundException() {
		try {
	    givenRollbackException(new RollbackException())
		.whenEntityExists(() -> {throw new EntityNotFoundException(VAL0002E_INVALID_VALUE, "dummy");}) // Dummy reason code.
		.thenThrow(new UniqueKeyConstraintViolationException(VAL0002E_INVALID_VALUE, // Dummy reason code.
															 key("unit_test", "dummy")));
		} catch (Exception e) {
		    fail("Unexpected exception: "+e);
		}
	}

	@Test
	public void throw_unique_key_constraint_violation_exception_if_entity_does_not_exist() {
		exception.expect(UniqueKeyConstraintViolationException.class);
		givenRollbackException(new RollbackException())
		.whenEntityExists(() -> new Object())
		.thenThrow(new UniqueKeyConstraintViolationException(VAL0002E_INVALID_VALUE, // Dummy reason code.
															 key("unit_test", "dummy")));
	}

	
}
