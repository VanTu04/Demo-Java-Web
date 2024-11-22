package com.project.shopapp.repositories;

import com.project.shopapp.models.InVoice;
import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Repository
public interface InVoiceRepository extends JpaRepository<InVoice, Long> {
    List<InVoice> findAllByUser(User user);
}
