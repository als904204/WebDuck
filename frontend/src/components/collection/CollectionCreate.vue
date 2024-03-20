<template>
  <div class="p-4 grid justify-content-center">
    <div class="col-12 md:col-6 lg:col-4">
      <div class="flex flex-column gap-2">

          <label for="title" >보관함 제목</label>
          <InputText id="title" v-model="title" size="large" placeholder="제목을 입력해 주세요"/>

        <div class="mb-2">
          <div v-if="validateTitle">
            <InlineMessage severity="error">{{validateTitle}}</InlineMessage>
          </div>
          <div v-else>
            <small id="title-help" class="mb-3 font-light">제목 입력해주세요.최소2자에서 최대 20자까지 가능합니다</small>
          </div>
        </div>


        <label for="description" class="mt-3">보관함 설명</label>
        <Textarea id="description" v-model="description" autoResize rows="5" class="w-full" placeholder="설명을 입력해 주세요" />

        <div class="mb-3">
          <div v-if="validateDesc">
            <InlineMessage severity="error">{{validateDesc}}</InlineMessage>
          </div>
          <div v-else>
            <small id="description-help" class="mb-3 font-light">설명을 입력해주세요.최소2자에서 최대 200자까지 가능합니다</small>
          </div>
        </div>

        <!-- 웹툰 선택 Modal -->
        <Button label="웹툰 선택" @click="modalBtn"  severity="success"/>

        <div class="mb-4">
          <div v-if="validateIds">
            <InlineMessage severity="error">{{validateIds}}</InlineMessage>
          </div>
          <div v-else>
            <small class="mb-3 font-light">최소 1개 이상의 웹툰을 선택하셔야 됩니다</small>
          </div>
        </div>

        <div class="grid">
          <Dialog v-model:visible="visible" modal header="웹툰 목록" :style="{ width: '50rem' }" :breakpoints="{ '1199px': '75vw', '575px': '90vw' }">
            <div>

              <!-- 장르 목록 버튼 -->
              <div class="genre-list grid p-3 gap-2">
                <Button v-for="genre in genres" :key="genre.genreName"
                        :label="genre.genreName" class="p-button"
                        :class="{ 'selected': selectedGenres.includes(genre.genreName) }"
                        @click="toggleGenre(genre.genreName)" />
              </div>

              <div v-if="message">
                <div v-if="messageType === 'error'">
                  <Message severity="error">{{ message }}</Message>
                </div>
                <div v-else-if="messageType === 'warning'">
                  <Message severity="warn">{{ message }}</Message>
                </div>
              </div>

              <div class="webtoon-list grid p-3">
                <div class="col-3" v-for="webtoon in webtoons" :key="webtoon.id">
                  <img :src="webtoon.imagePath" class="max-w-full" :alt="webtoon.title">
                  <span>
                    <span>{{ webtoon.title }}</span>
                    <span>
                         <input type="checkbox" :checked="selectedWebtoonIds.includes(webtoon.id)" @change="updateSelectedWebtoons(webtoon.id,webtoon.title)" />
                    </span>
                  </span>
                </div>
              </div>

              <div class="flex justify-content-center">
                <Button label="선택 완료" size="large" rounded icon="pi pi-check" @click="visible = false"></Button>
              </div>

            </div>
          </Dialog>
        </div>

        <div class="mb-1">
          <Card>
            <template #title> <i class="pi pi-server"></i>선택한 웹툰 목록</template>
            <template #content>
              <p class="m-0">
                <div class="flex grid gap-1">
                  <div v-for="webtoon in selectedWebtoonTitle" :key="webtoon.id" class="pr-2 mb-1">
                    <Tag severity="info" :value="webtoon" rounded></Tag>
                  </div>
                </div>
              </p>
            </template>
          </Card>
        </div>




        <Button label="등록" class="mt-1" @click="createMyCollection" />
      </div>
    </div>
  </div>
</template>

<script setup>


import {ref} from 'vue';
import {createCollection} from "../../service/CollectionService.js";
import {getGenres, getWebtoonsByGenres} from "../../service/WebtoonListFetchService.js";
import {engGenresToKorean, korGenresToEng} from '../common/genreConverter.js';
import Message from "primevue/message";
import {isLoggedIn} from "../../store/auth.js";
import {router} from "../../router/index.js";

// 모달
const title = ref('');
const description = ref('');
const selectedWebtoonIds = ref([]);
const selectedWebtoonTitle = ref([]);
const visible = ref(false);


// 장르
const genres = ref([]);         // 장르 목록
const selectedGenres = ref([]); // 여러 장르를 선택할 수 있도록 배열로 관리
const message = ref('');
const messageType = ref('');
let webtoons = ref([]);

// 검증
const validateTitle = ref('');
const validateDesc = ref('');
const validateIds = ref('');

// 선택된 웹툰 추출

// 컬렉션 저장
const createMyCollection = async () => {

  if(!isLoggedIn.value){
    alert('로그인이 필요합니다')
    return;
  }

  validateTitle.value = '';
  validateDesc.value = '';
  validateIds.value = '';

  if (!title.value || title.value.length < 2 || title.value.length > 20) {
    validateTitle.value = '제목은 최소2자에서 최대 20자까지 가능합니다';
    return;
  }

  else if (!description.value || description.value.length < 2 || description.value.length > 200) {
    validateDesc.value = '설명은 2자 이상 200자 이하로 입력해야 합니다.';
    return;
  }

  else if (selectedWebtoonIds.value.length === 0) {
    validateIds.value = '하나 이상의 웹툰을 선택해야 합니다';
    return;
  }


  try {
    const collectionData = {
      title: title.value,
      description:description.value,
      webtoonIds: selectedWebtoonIds.value,
    };

    await createCollection(collectionData);
    alert('보관함을 생성하였습니다')
    await router.push({name: 'collection'});
  } catch (err) {
    alert('알 수 없는 에러가 발생했습니다.')
  }
};

// 모달 버튼 누를 시, 장르 목록 요청
const modalBtn = async () => {
  visible.value = true;
  await genreList();
};



const updateSelectedWebtoons = (webtoonId, title) => {
  const index = selectedWebtoonIds.value.indexOf(webtoonId);
  if (index > -1) {
    // 이미 선택한 웹툰이라면 삭제
    selectedWebtoonIds.value.splice(index, 1);
    selectedWebtoonTitle.value.splice(title, 1);
  } else {
    selectedWebtoonIds.value.push(webtoonId);
    selectedWebtoonTitle.value.push(title);
  }
};

const genreList = async ()=> {
  const response = await getGenres();

  genres.value = response.map(genre => ({
    genreName: engGenresToKorean[genre.genreName] || genre.genreName
  }));

  if (genres.value.length > 0) {
    const initialGenre = genres.value[0].genreName;
    await toggleGenre(initialGenre);
  }

}



const toggleGenre = async (genreName) => {
  const genreKey = korGenresToEng[genreName] || genreName;
  const index = selectedGenres.value.findIndex(g => korGenresToEng[g] === genreKey);

  if (index > -1) {
    // 이미 선택된 장르면 제거
    selectedGenres.value.splice(index, 1);
  } else {
    // 선택되지 않은 장르면 추가
    selectedGenres.value.push(genreName);
  }
  try {
    // 선택된 장르(영어로 변환된)로 웹툰 목록 요청
    webtoons.value = await getWebtoonsByGenres(selectedGenres.value.map(g => korGenresToEng[g]));
    message.value = '';
    messageType.value = '';
  } catch (error) {
    if (error.response && error.response.status === 404) {
      message.value = '찾으시는 웹툰이 없어요! 장르를 다시 선택해 주세요';
      webtoons.value = [];
      messageType.value = 'warning' ;
    } else if (error.response && error.response.status === 400) {
      message.value = '선택된 장르가 없습니다. 장르를 선택해 주세요';
      webtoons.value = [];
      messageType.value = 'error';
    } else {
      message.value = '알수없는 오류가 발생했습니다.';
      webtoons.value = [];
      messageType.value = 'error';
    }
  }
};
</script>

<style scoped>

/* 선택된 버튼에 적용할 스타일 */
.selected {
  background-color: #007bff;
  color: white;
  border : none;
}

</style>
