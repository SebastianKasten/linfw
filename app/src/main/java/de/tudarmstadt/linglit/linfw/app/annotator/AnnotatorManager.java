package de.tudarmstadt.linglit.linfw.app.annotator;

import com.google.common.base.Optional;

import de.tudarmstadt.linglit.linfw.app.model.workspace.Annotable;
import de.tudarmstadt.linglit.linfw.app.plugin.AnnotatorSupplier;

/**
 * An annotator manager holds annotator suppliers and can have two
 * states: valid and invalid. A valid annotator contains the dependencies
 * for any of its member.
 * 
 * @author Sebastian Kasten <sebastian.kasten@gmail.com>
 *
 */
public interface AnnotatorManager {
	/**
	 * Checks if this manager is valid, i.e. all the
	 * required types of any member can be satisfied
	 * by another member.
	 * 
	 * @return true, if this manager is valid; false, if not
	 */
	public boolean valid();
	
	/**
	 * Checks if this manager supports the given annotation type, i.e.
	 * a member of this manager produces annotations of the type.
	 * 
	 * @param type type to check
	 * @return true, if it supports the type; false, if not
	 */
	public boolean supports(Class<?> type);
	
	/**
	 * Returns the annotator supplier for the given type.
	 * 
	 * @param type type of the annotator supplier
	 * @return an annotator supplier producing an annotator for the given type or
	 * <code>Optional.absent()</code> if not supported
	 */
	public Optional<AnnotatorSupplier> forType(Class<?> type);
	
	/**
	 * Tries to apply all annotators to the given annotable.
	 * 
	 * @param annotable annotable object
	 * @return true, if the annotation process was successful;
	 * false, if not
	 */
	public boolean annotate(Annotable annotable);
	
	/**
	 * Adds an annotator to this manager.
	 * 
	 * @param supplier annotator supplier to be added
	 * @return true, if the manager is valid after the addition; false if not
	 */
	public boolean add(AnnotatorSupplier supplier);
	
	/**
	 * Removes an annotator from this manager.
	 * 
	 * @param supplier annotator supplier to be removed
	 * @return true, if the manager is valid after the removal; false if not
	 */
	public boolean remove(AnnotatorSupplier supplier);
	
	/**
	 * Removes the annotator of the given type from this manager.
	 * 
	 * @param type type of the annotator to be removed
	 * @return true, if the manager is valid after the removal; false if not
	 */
	public boolean remove(Class<?> type);
}
