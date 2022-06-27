package io.leitstand.commons.model;

import static java.lang.String.format;

import java.util.function.Supplier;
import java.util.logging.Logger;

import javax.persistence.PersistenceException;
import javax.transaction.RollbackException;

import io.leitstand.commons.EntityNotFoundException;
import io.leitstand.commons.UniqueKeyConstraintViolationException;


public class RollbackExceptionUtil {
	
	private static final Logger LOG = Logger.getLogger(RollbackExceptionUtil.class.getName());


	
	public static RollbackExceptionUtil givenRollbackException(Exception e) {
		return new RollbackExceptionUtil(e);
	}
	
	private Exception cause;
	private Supplier<?> supplier;

	
	private RollbackExceptionUtil(Exception cause) {
		this.cause = cause;
	}

	/**
	 * Creates an instance to test whether an entity exists.
	 * @param t the test to check whether an entity exists. 
	 * @return a reference to this util to proceed with the error handling fluently.
	 */
	public RollbackExceptionUtil whenEntityExists(Supplier<?> supplier) {
		this.supplier = supplier;
		return this;
	}
	

	/**
	 * Runs an action to resolve the rollback exception.
	 * @param action the action to resolve the rollback exception.
	 */
	public void thenDo(Runnable action) {
		action.run();
	}
	
	
	/**
	 * Fires the specified {@link UniqueKeyConstraintViolationException} if the entity exists.
	 * @param e the unique key constraint violation to be reported
	 */
	public void thenThrow(UniqueKeyConstraintViolationException e) {
		try {
			if(cause.getClass() != RollbackException.class && cause.getClass() != PersistenceException.class) {
				return;
			}
			
			if(supplier.get() != null) {
				throw e;
			}
		} catch (EntityNotFoundException notfound) {
			// No action required.
			LOG.fine(()->format("Entity not found: %s",e.getMessage()));
		}
	}
	
	
}
