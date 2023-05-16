package com.example.newWiki.topic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.newWiki.topic.model.Topic;

@Repository
@EnableJpaRepositories
public interface TopicRepo extends JpaRepository<Topic, Long>{


	
	List<Topic> findByTopicNameContaining(String topicName);

	List<Topic> findAllByTopicId(Long topicId);

	Optional<Topic> findByTopicId(Long topicId);

	


}
