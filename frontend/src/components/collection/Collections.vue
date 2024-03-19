<template>



<div class="p-4">

    <div class="grid">
      <Button @click="createMyCollection" class="col-6 col-offset-3 text-center mb-4" label="내 보관함 만들기"></Button>
    </div>

    <div class="grid">
      <div v-for="collection in collections" :key="collection.id" class="col-12 sm:col-6 md:col-4 lg:col-3 xl:col-3 line-height-4" >
        <router-link :to="`/collection/${collection.id}`" class="custom-link" >
          <div class="text-center p-3 border-round-sm hover:bg-black-alpha-10 border-1 font-bold">
            <p class="text-overflow-ellipsis overflow-hidden"> {{collection.title}} </p>
            <p class="text-overflow-ellipsis overflow-hidden "> {{collection.description}} </p>
            <hr/>
            <i class="pi pi-user"><span><span class="font-bold px-1">{{collection.ownerName}}</span><span class="font-light">님의 보관함</span></span></i>
            <hr/>

            <!-- 작성자 수정 삭제-->
            <div v-if="collection.owner" class="flex justify-content-center gap-3">
              <div>
                <Button label="수정" severity="info" @click.prevent="updateMyCollection(collection.id)"></Button>
              </div>
              <div>
                <Button label="삭제" severity="danger" @click.prevent="deleteMyCollection(collection.id)"></Button>
              </div>
            </div>

          </div>
        </router-link>
      </div>
    </div>


</div>








</template>

<script setup>
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";
import {deleteCollection, fetchAllCollections} from "../../service/CollectionService.js";
import {isLoggedIn} from "../../store/auth.js";

const router = useRouter();
const collections = ref([]);


onMounted(async () => {
  await loadCollections();
});

const loadCollections = async () => {
  try {
    collections.value = await fetchAllCollections();
  } catch (err) {
    alert("알 수 없는 에러가 발생했습니다");
  }
};



const createMyCollection = () => {
  if(!isLoggedIn.value){
    alert('로그인이 필요합니다')
    return;
  }
  router.push({ name: 'collection/add' });
};

const deleteMyCollection = (id) => {
  try{
    if (confirm("정말로 삭제하시겠습니까?")) {
      deleteCollection(id);
      alert('삭제되었습니다.');
      loadCollections()
    }
  }catch (err){
    alert('삭제하는도중 알 수 없는 에러가 발생했습니다')

  }
}

const updateMyCollection = (id) => {
  if(!isLoggedIn.value){
    alert('로그인이 필요합니다')
    return;
  }
  router.push({
    name: 'collection.update' ,
    params: { id: id }
  });
}




</script>

<style scoped>
.custom-link {
  text-decoration: none; /* 밑줄 제거 */
  color: inherit; /* 부모 요소로부터 글자 색상 상속 */
}


</style>