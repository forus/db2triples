/***************************************************************************
 *
 * R2RML Model : ObjectMap class
 *
 * (C) 2011 Antidot (http://www.antidot.net)
 *
 * Module			:	R2RMLModel
 * 
 * Fichier			:	ObjectMap.java
 *
 * Description		:	An element of this class, called a ObjectMap,
 * 						contains the rules for generating the object 
 * 						component of the (predicate, object) pair
 * 						generated by a PredicateObjectMap.
 * 
 * Reference		:	RDF Vocabulary for R2RML
 * 						R2RML: RDB to RDF Mapping Language
 * 						W3C Working Draft 24 March 2011
 *
 * Options de compilation:
 *
 * Auteurs(s)			:	JHO
 *
 *
 ****************************************************************************/
package antidot.r2rml.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openrdf.model.Value;

import antidot.r2rml.exception.InvalidR2RMLStructureException;
import antidot.r2rml.exception.InvalidR2RMLSyntaxException;
import antidot.r2rml.tools.R2RMLToolkit;
import antidot.xmls.type.XSDType;

/**
 * @author  jh
 */
public class ObjectMap implements IObjectMap {
	
	// Log
	private static Log log = LogFactory.getLog(TriplesMap.class);

	/**
	 */
	private Value object;
	/**
	 */
	private String column;
	/**
	 */
	private String template;
	/**
	 */
	private XSDType datatype;
	/**
	 */
	private String language;
	/**
	 */
	private String inverseExpression;
	/**
	 */
	private TermType termType;

	public ObjectMap(Value object, String column, String template,
			XSDType datatype, String language, String inverseExpression,
			TermType termType) throws InvalidR2RMLSyntaxException, InvalidR2RMLStructureException {
		super();

		// Check properties consistency
		if ((column == null) && (object == null) && (template == null)) {
			throw new InvalidR2RMLStructureException(
					"[ObjectMap:ObjectMap] column or object or template have to be specified.");
		} else if ((column != null) && (object != null)) {
			throw new InvalidR2RMLStructureException(
					"[ObjectMap:ObjectMap] Ambiguity between column and object : the two "
							+ "are specified but just one is required.");
		} else if ((column != null) && (template != null)) {
			throw new InvalidR2RMLStructureException(
					"[ObjectMap:ObjectMap] Ambiguity between column and template : the two "
							+ "are specified but just one is required.");
		} else if ((object != null) && (template != null)) {
			throw new InvalidR2RMLStructureException(
					"[ObjectMap:ObjectMap] Ambiguity between object and template : the two "
							+ "are specified but just one is required.");
		}
		// Check termType
		// Literal by default
		if (termType == null)
			termType = TermType.LITERAL;
		// Check datatype
		// String by default
		if ((template != null) && (datatype == null))
			datatype = XSDType.STRING;
		if ((column != null) && (datatype == null))
			if (log.isInfoEnabled()) log.info("[ObjectMap:ObjectMap] Default datatype corresponding to column definition in logical table : " +
					"it will extracted from database during query processing time.");
		// Check relevance
		if ((datatype != null) && (column != null || template != null) && (termType.equals(TermType.LITERAL))){
			if (log.isWarnEnabled()) log.warn("[ObjectMap:ObjectMap] Use of datatype is this case is not relevant.");
		}

		this.object = object;
		this.column = column;
		setTemplate(template);
		this.datatype = datatype;
		this.language = language;
		setInverseExpression(inverseExpression);
		this.termType = termType;
	}

	/**
	 * @return
	 */
	public Value getObject() {
		return object;
	}

	/**
	 * @param object
	 */
	public void setObject(Value object) {
		this.object = object;
	}

	/**
	 * @return
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * @param column
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * @return
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * @param template
	 * @throws InvalidR2RMLSyntaxException
	 */
	public void setTemplate(String template) throws InvalidR2RMLSyntaxException {
		if (!R2RMLToolkit.checkCurlyBraces(template))
			throw new InvalidR2RMLSyntaxException(
					"A synthax error have be found in your R2RML expression : " + template);
		this.template = template;
	}

	/**
	 * @return
	 */
	public XSDType getDatatype() {
		return datatype;
	}

	/**
	 * @param datatype
	 */
	public void setDatatype(XSDType datatype) {
		this.datatype = datatype;
	}

	/**
	 * @return
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return
	 */
	public String getInverseExpression() {
		return inverseExpression;
	}

	/**
	 * @param inverseExpression
	 */
	public void setInverseExpression(String inverseExpression) {
		if (!R2RMLToolkit.checkCurlyBraces(inverseExpression))
			throw new IllegalArgumentException(
					"A synthax error have be found in your R2RML expression : " + inverseExpression);
		this.inverseExpression = inverseExpression;
	}

	/**
	 * @return
	 */
	public TermType getTermType() {
		return termType;
	}

	/**
	 * @param termType
	 */
	public void setTermType(TermType termType) {
		this.termType = termType;

	}

}