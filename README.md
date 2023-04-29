# A FTP based cloud sotrage system
## A microservices based file storage system where file can be stored and retrived from the remote server.

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

