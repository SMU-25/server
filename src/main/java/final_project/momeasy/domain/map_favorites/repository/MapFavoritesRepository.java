package final_project.momeasy.domain.map_favorites.repository;

import final_project.momeasy.domain.map_favorites.entity.MapFavorites;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MapFavoritesRepository extends JpaRepository<MapFavorites, Long> {
    @Query("SELECT mf FROM MapFavorites mf WHERE mf.parent.id = :parentId AND mf.id < :cursor ORDER BY mf.id DESC")
    Slice<MapFavorites> findMapFavoritesCursorPagination(@Param("parentId") Long parentId, Long cursor, Pageable pageable);

    @Query("""
            SELECT COUNT(mf) > 0 FROM MapFavorites mf
            WHERE mf.id = :mapId AND mf.parent.id = :parentId""")
    boolean existsByMapIdAndParentId(@Param("mapId") Long mapId, @Param("parentId") Long parentId);
}
