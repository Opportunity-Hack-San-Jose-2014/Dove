SET job.name 'APIIntel';
SET mapred.job.queue.name pp_risk_dst;
set parallel 32

REGISTER ../hadoop/*.jar

-- DEFINE MATCHING org.work2future.udf.Matching();
-- load data 
--data = LOAD '$input' USING PigStorage(',') AS (jobid: chararray, bio: chararray, desc: chararray);
data = LOAD '$input' USING PigStorage(',') AS (jobid: chararray, bio_desc: chararray);
--score =  FOREACH data GENERATE *,  org.work2future.udf.Matching(bio,desc);
score =  FOREACH data GENERATE *,  org.work2future.udf.Matching(bio_desc);

dump score
