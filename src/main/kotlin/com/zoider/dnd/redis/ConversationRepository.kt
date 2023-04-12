package com.zoider.dnd.redis

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ConversationRepository: CrudRepository<ConversationState, String>