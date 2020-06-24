package com.ajith.product.persistence.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ajith.product.persistence.entity.Product;

public class ProductSpecification implements Specification<Product> {

	/**
	 * @author Ajith Kumar
	 * 
	 *         JPA Specifications allow us to create dynamic database queries by
	 *         using the JPA Criteria API. It defines a specification as a predicate
	 *         over an entity This feature is very useful when we have many number
	 *         of input query combinations. It will help us to create dynamic
	 *         queries Specification requires, SearchCriteria and SearchOperation
	 *         classes to create dynamic CRIETERIA queries
	 */
	private static final long serialVersionUID = -3552839953523162914L;

	private List<SearchCriteria> searchCriteriaList;

	public ProductSpecification() {
		this.searchCriteriaList = new ArrayList<>();
	}

	public void addCriteria(SearchCriteria criteria) {
		searchCriteriaList.add(criteria);
	}

	@Override
	public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		List<Predicate> predicates = new ArrayList<>();

		for (SearchCriteria criteria : searchCriteriaList) {
			if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
				predicates
						.add(criteriaBuilder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
				predicates.add(criteriaBuilder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(criteria.getKey()),
						criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
				predicates.add(
						criteriaBuilder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
			} else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
				predicates.add(criteriaBuilder.notEqual(root.get(criteria.getKey()), criteria.getValue()));
			} else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
				predicates.add(criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue()));
			} else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(criteria.getKey())),
						"%" + criteria.getValue().toString().toLowerCase() + "%"));
			} else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(criteria.getKey())),
						criteria.getValue().toString().toLowerCase() + "%"));
			} else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(criteria.getKey())),
						"%" + criteria.getValue().toString().toLowerCase()));
			} else if (criteria.getOperation().equals(SearchOperation.IN)) {
				predicates.add(criteriaBuilder.in(root.get(criteria.getKey())).value(criteria.getValue()));
			} else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
				predicates.add(criteriaBuilder.not(root.get(criteria.getKey())).in(criteria.getValue()));
			}
		}

		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

	}

}
