/* 
 * Copyright 2011-2013 Antidot opensource@antidot.net
 * https://github.com/antidot/db2triples
 * 
 * This file is part of DB2Triples
 *
 * DB2Triples is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation; either version 2 of 
 * the License, or (at your option) any later version.
 * 
 * DB2Triples is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/***************************************************************************
 *
 * R2RML Model : Standard PredicateObjectMap Class
 *
 * A predicate-object map is a function
 * that creates predicate-object pairs from logical 
 * table rows. It is used in conjunction with a subject
 * map to generate RDF triples in a triples map.
 * 
 ****************************************************************************/
package net.antidot.semantic.rdf.rdb2rdf.r2rml.model;

import java.util.HashSet;
import java.util.Set;

public class StdPredicateObjectMap implements PredicateObjectMap {

	private Set<ObjectMap> objectMaps;
	private Set<ReferencingObjectMap> refObjectMaps;
	private Set<PredicateMap> predicateMaps;
	protected TriplesMap ownTriplesMap;
	private HashSet<GraphMap> graphMaps;

	private StdPredicateObjectMap(Set<PredicateMap> predicateMaps) {
		setPredicateMaps(predicateMaps);
	}

	public StdPredicateObjectMap(Set<PredicateMap> predicateMaps,
			Set<ObjectMap> objectMaps) {
		this(predicateMaps);
		setObjectMaps(objectMaps);
	}
	
	public StdPredicateObjectMap(Set<PredicateMap> predicateMaps,
			Set<ObjectMap> objectMaps, Set<ReferencingObjectMap> referencingObjectMaps) {
		this(predicateMaps, objectMaps);
		setReferencingObjectMap(referencingObjectMaps);
	}

	public void setReferencingObjectMap(Set<ReferencingObjectMap> refObjectMaps) {
		if (refObjectMaps == null)
			this.refObjectMaps = new HashSet<ReferencingObjectMap>();
		else {
			for (ReferencingObjectMap refObjectMap : refObjectMaps) {
				if (refObjectMap != null)
					refObjectMap.setPredicateObjectMap(this);
			}
			this.refObjectMaps = refObjectMaps;
		}
	}

	public Set<ObjectMap> getObjectMaps() {
		return objectMaps;
	}

	public Set<PredicateMap> getPredicateMaps() {
		return predicateMaps;
	}

	public Set<ReferencingObjectMap> getReferencingObjectMaps() {
		return refObjectMaps;
	}

	public boolean hasReferencingObjectMaps() {
		return refObjectMaps != null && !refObjectMaps.isEmpty();
	}

	public TriplesMap getOwnTriplesMap() {
		return ownTriplesMap;
	}

	public void setObjectMaps(Set<ObjectMap> objectMaps) {
		if (objectMaps == null)
			this.objectMaps = new HashSet<ObjectMap>();
		else {
			for (ObjectMap objectMap : objectMaps) {
				if (objectMap != null)
					objectMap.setPredicateObjectMap(this);
			}
			this.objectMaps = objectMaps;
		}
	}

	public void setOwnTriplesMap(TriplesMap ownTriplesMap) {
		// Update triples map if not contains this subject map
		if (ownTriplesMap.getSubjectMap() != null)
			if (!ownTriplesMap.getPredicateObjectMaps().contains(this))
				ownTriplesMap.addPredicateObjectMap(this);
		this.ownTriplesMap = ownTriplesMap;
	}

	public void setPredicateMaps(Set<PredicateMap> predicateMaps) {
		if (predicateMaps == null)
			this.predicateMaps = new HashSet<PredicateMap>();
		else {
			for (PredicateMap predicateMap : predicateMaps) {
				if (predicateMap != null)
					predicateMap.setPredicateObjectMap(this);
			}
			this.predicateMaps = predicateMaps;
		}
	}
	
	public Set<GraphMap> getGraphMaps() {
		return graphMaps;
	}
	
	public void setGraphMaps(Set<GraphMap> graphMaps) {
		this.graphMaps = new HashSet<GraphMap>(graphMaps);
	}

}
