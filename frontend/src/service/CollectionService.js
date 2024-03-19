import axios from "axios";

export const fetchAllCollections = async()=>{
  try {
    const response = await axios.get('/api/v1/collection');
    return response.data;
  } catch (err){
    throw err;
  }

}

export const fetchCollectionById = async(id) => {
  try{
    const response = await axios.get(`/api/v1/collection/${id}`);
    return response.data;
  }catch (err){
    throw err;
  }
}

export const fetchMyCollectionById = async (id) => {
  try{
    const response = await axios.get(`/api/v1/collection/${id}/member`);
    return response.data;
  }catch (err){
    throw err;
  }
}

export const createCollection = async (collectionData) => {
  try{
    const response = await axios.post('/api/v1/collection', collectionData);
    return response.data;
  } catch (err) {
    throw err;
  }
}

export const deleteCollection = async (id) => {
  try{
    const response = await axios.delete(`/api/v1/collection/${id}`);
    return response.data;
  }catch (err){
    throw err;
  }
}

export const updateCollection = async (id,collectionUpdate) =>{
  try {
    const response = await axios.patch(`/api/v1/collection/${id}`,collectionUpdate);
    return response.data;
  } catch (err){
    throw err;
  }
}