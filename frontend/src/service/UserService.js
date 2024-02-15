import axios from "axios";

export const fetchUserInfo = async () => {
  try{
    const response = await axios.get(`/api/v1/member/profile`);
    return response.data;
  }catch (error){

  }
}

export const updateNickname = async (username) => {
  try {
    const response = await axios.patch(`/api/v1/member/profile`,{
      username
    });
    return response.data;
  } catch (error) {
    if (error.response) {
      if (error.response.status === 409) {
        throw new Error("409");
      } else if (error.response.status === 400) {
        throw new Error("400");
      } else if (error.response.status === 404) {
        throw new Error("404");
      }
    }
    throw new Error("500");
  }
}