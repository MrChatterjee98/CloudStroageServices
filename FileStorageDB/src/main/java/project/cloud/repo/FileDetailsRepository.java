package project.cloud.repo;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import project.cloud.model.FileStorageInfo;

@Repository
public interface FileDetailsRepository extends MongoRepository<FileStorageInfo,String> {

	@Query("{'fileName':'?0'}")
	FileStorageInfo findFileByFileName(String fileName);
}
