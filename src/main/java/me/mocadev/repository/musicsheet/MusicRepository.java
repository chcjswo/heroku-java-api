package me.mocadev.repository.musicsheet;

import me.mocadev.document.musicsheet.Music;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-19
 **/
public interface MusicRepository extends MongoRepository<Music, String> {

}
