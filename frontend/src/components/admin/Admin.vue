<template>
  <div>
    <div class="grid">
      <div class="col-12 md:col-6 lg:col-4">
        <div class="text-center p-3 border-round-sm border-500 border-1 font-bold">
          <div class="mb-5">
            <span>웹툰 관리<i class="pi pi-book pl-2" ></i></span>
          </div>
          <hr/>
          <div class="mb-5">
            <div class="card flex justify-content-center mt-3">
              <Button label="웹툰 자동 저장 메뉴" @click="webtoonSaveVisible = true" :disabled="webtoonSaveLoading" />

              <Dialog v-model:visible="webtoonSaveVisible" modal header="웹툰 자동 저장 요청" :style="{ width: '25rem' }">
                <span class="p-text-secondary block mb-5">
                  <a href="https://github.com/HyeokjaeLee/korea-webtoon-api" target="_blank" class="text-black-alpha-90">Korea Webtoon API</a> 와
                  <a href="https://www.kmas.or.kr/guide/openapi" target="_blank" class="text-black-alpha-90">만화 규장각 API</a> 를 요청하여, 자동으로 웹툰 데이터 추출 후 저장합니다.</span>
                <div class="flex align-items-center gap-3 mb-3 justify-content-center">
                  <Button label="카카오 웹툰 자동 저장 요청" class="bg-yellow-400 border-yellow-400" @click="saveWebtoons('KAKAO')"></Button>
                </div>
                <div class="flex align-items-center gap-3 mb-5 justify-content-center">
                  <Button label="네이버 웹툰 자동 저장 요청"  @click="saveWebtoons('NAVER')"></Button>
                </div>
              </Dialog>

            </div>
          </div>

          <div class="mb-5">
            <div>
              <div class="card" v-if="webtoonSaveLoading" >
                <p>API 웹툰 데이터 요청중입니다. 약 1분정도 소요됩니다.</p>
                <ProgressBar mode="indeterminate" style="height: 6px"></ProgressBar>
              </div>
            </div>

            <div v-if="webtoonSaveResponses.length > 0">
              <ul>
                <li v-for="(response, index) in webtoonSaveResponses" :key="index">
                  {{ response.platform }} 요청 성공: {{ response.count }}개 저장 완료
                </li>
              </ul>
            </div>
          </div>


          <div>
            <Button label="중복 웹툰 삭제" @click="webtoonDeleteVisible = true" :disabled="webtoonDeleteLoading" severity="danger"/>

            <Dialog v-model:visible="webtoonDeleteVisible" modal header="중복 웹툰 삭제" :style="{ width: '25rem' }">
                <span class="p-text-secondary block mb-5">
                  <p>중복 저장된 웹툰을 삭제합니다.</p>
                </span>
              <div class="flex align-items-center gap-3 mb-3 justify-content-center">
                <Button label="중복 웹툰 삭제 요청" class="bg-yellow-400 border-yellow-400" @click="deleteDuplicateWebtoons()"></Button>
              </div>
            </Dialog>
          </div>

        </div>



      </div>
      <div class="col-12 md:col-6 lg:col-4">
        <div class="text-center p-3 border-round-sm border-500 border-1 font-bold">
          <div class="mb-5">
            <span>회원 관리<i class="pi pi-server pl-2" ></i></span>
          </div>
          <hr/>

          <div class="card flex justify-content-center mt-3">
            <Button label="회원 모니터링" severity="info" @click="memberMonitor" />
          </div>
        </div>
      </div>
      <div class="col-12 md:col-6 lg:col-4">
        <div class="text-center p-3 border-round-sm border-500 border-1 font-bold">
          <div class="mb-5">
            <span>서버 관리<i class="pi pi-server pl-2" ></i></span>
          </div>
          <hr/>

          <router-link to="/monitor" class="no-underline">
            <div class="card flex justify-content-center mt-3">
              <Button severity="help"><span class="mr-2">메모리 모니터링</span>  <i class="pi pi-desktop"></i></Button>
            </div>
          </router-link>
        </div>

      </div>
    </div>

  </div>

</template>

<script setup>
import { ref } from "vue";
import {DuplicateWebtoons, WebtoonSaveApi} from "../../service/AdminService.js";

// 웹툰 저장 요청
const webtoonSaveVisible = ref(false);
const webtoonSaveLoading = ref(false);
const webtoonSaveResultStatus = ref(null);
const webtoonSaveResponses = ref([]);

// 웹툰 삭제 요청
const webtoonDeleteVisible = ref(false);
const webtoonDeleteLoading = ref(false);
const webtoonDeleteResponses = ref();

const saveWebtoons = async (platform) => {

  // 이미 요청중이라면 종료
  if (webtoonSaveLoading.value) {
    alert('요청 진행중입니다.')
    return;
  }

  webtoonSaveLoading.value = true; // 요청 시작 시 로딩 시작
  webtoonSaveVisible.value = false;

  try {
    const response = await WebtoonSaveApi(platform);
    webtoonSaveResponses.value.push({
      platform: response.platform,
      count: response.count
    });
    alert(`${response.platform}웹툰 ${response.count}개 저장완료!`);
    webtoonSaveResultStatus.value = true;
  } catch (error) {
    webtoonSaveResultStatus.value = false;
  } finally {
    webtoonSaveLoading.value = false; // 요청 완료 시 로딩 종료
  }
}

const deleteDuplicateWebtoons = async () => {
  if (webtoonDeleteLoading.value) {
    alert('요청 진행중입니다.')
    return;
  }

  webtoonDeleteLoading.value = true;
  webtoonDeleteVisible.value = false;

  try{
    webtoonDeleteResponses.value = await DuplicateWebtoons();

    if (webtoonDeleteResponses.value === 0) {
      alert('중복된 웹툰이 없습니다');
    }else {
      alert(`총 ${webtoonDeleteResponses.value} 개의 중복된 웹툰이 삭제되었습니다.`);
    }
  }catch (error){
    alert('중복 웹툰 데이터 삭제 중 오류가 발생했습니다.');
  }finally {
    webtoonDeleteLoading.value = false;
  }


}
const memberMonitor = async () => {
  alert('아직 준비중입니다.')
}


</script>

<style>
.no-underline {
  text-decoration: none;
}

</style>
