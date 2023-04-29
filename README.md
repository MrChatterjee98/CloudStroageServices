# A FTP based cloud sotrage system
## A microservices based file storage system where file can be stored and retrived from the remote server.
## Architecture 
![image](https://user-images.githubusercontent.com/59931878/235321025-c88af8a6-689c-4baa-9c98-b150aeccc8ed.png)


### Services:
- Cloud Storage : This services is used to upload file and send it to the storage server usign FTP(apache commons). it communicates to the CENT OS file server and
sends the uploaded files to the server.
- File Storage DB : This sercie is used to save the file upload information and MD5 value of that file to a mongo db databse.
- Registry : Service registry for all sercvices to complete the service to service communicaiton.
- Config server : cloud config server where all the configuration files for the other services is kept.
- Gateway : for server side load balancing and making all the API for acessing data is simpler.

### Communication:
- API server and CENT OS file storage server communciates via FTP.
- Cloud Storage and FIle Storage DB communicates with Kafka.
- Microservices communicate via REST API.

### FTP server:
- FTP Server is using CENT OS. which requires user authentication(default linux authentication) to use
- the server stores the files using their extension
e.g : text files are stored in TXT folder ,JSON files in JSON folder etc.

### FIle Storage DB:
- Mongo db is used to store the files infomration such as file name, creation date, download count.
- Eeach time a file is uploded an event is published to the kafka bus by the **Cloud Storage** service which is consume by the **File Storage** service
and the information is stored in the db

### Functionalities:
- Storing files
- previewing files
- downloading files


#### TODO:
* Phase 1: basic backend design.(completed)
* Phase 2: basic frontedn design .
* Phase 3: authentication using spring cloud.
