package com.example.Repositories;

import com.example.Entities.Like;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource
public interface LikeRepository extends PagingAndSortingRepository<Like, Long> {

    @Override
    @PreAuthorize("like?.user?.id == principal?.id")
    Like save(@Param("like") Like like);

    @Override
    @PreAuthorize("like?.user?.id == principal?.id")
    void delete(@Param("like") Like like);

}
