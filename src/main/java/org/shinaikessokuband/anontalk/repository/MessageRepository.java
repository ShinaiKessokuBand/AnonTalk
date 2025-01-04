package org.shinaikessokuband.anontalk.repository;

import org.shinaikessokuband.anontalk.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>
{

}
