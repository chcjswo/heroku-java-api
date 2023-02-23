package me.mocadev.herokujavaapi.repository.musicsheet;

import me.mocadev.herokujavaapi.document.musicsheet.Music;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author chcjswo
 * @version 1.0.0
 * @blog https://mocadev.tistory.com
 * @github https://github.com/chcjswo
 * @since 2023-02-19
 **/
public interface MusicRepository extends MongoRepository<Music, String> {

	Music findByRoomNameAndRoomPass(String roomName, String roomPass);

	Music findByRandomString(String randomString);

	Music findByRoomName(String roomName);
}
