package com.idus.backpacker.api.kernel.presentation.http.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

@ExtendWith({MockitoExtension.class})
@DisplayName("Kernel > Presentation > Http > Response > HttpResponse 테스트")
class HttpResponseTest {

    @Nested
    @DisplayName("Page 메타 정보 테스트")
    class PageTest {
        private HttpResponse<String> response;
        @Mock private Page<?> page;

        @Test
        @DisplayName("모든 아이템의 수를 메타 정보로 제공받을 수 있어야 한다")
        void should_be_able_to_get_totalElement_size_by_total() {
            var total = 9312849037412385L;
            when(page.getTotalElements()).thenReturn(total);
            response = HttpResponse.of("TEST", page);

            var result = response.getMeta().getTotal();

            assertThat(result).isEqualTo(total);
        }

        @Test
        @DisplayName("페이지별 아이템 최대 수를 메타 정보로 제공받을 수 있어야 한다")
        void should_be_able_to_get_size_by_perPage() {
            var size = 1239801023;
            when(page.getSize()).thenReturn(size);
            response = HttpResponse.of("TEST", page);

            var result = response.getMeta().getPerPage();

            assertThat(result).isEqualTo(size);
        }

        @Test
        @DisplayName("현재 페이지를 메타 정보로 제공받을 수 있어야 한다")
        void should_be_able_to_get_number_by_currentPage() {
            var number = 3;
            when(page.getNumber()).thenReturn(number);
            response = HttpResponse.of("TEST", page);

            var result = response.getMeta().getCurrentPage();

            assertThat(result).isEqualTo(number);
        }

        @Test
        @DisplayName("마지막 페이지 번호를 메타 정보로 제공받을 수 있어야 한다")
        void should_be_able_to_get_totalPages_by_lastPage() {
            var totalPages = 99;
            when(page.getTotalPages()).thenReturn(totalPages);
            response = HttpResponse.of("TEST", page);

            var result = response.getMeta().getLastPage();

            assertThat(result).isEqualTo(totalPages);
        }

        @Test
        @DisplayName("현재 페이지의 원소 수를 메타 정보로 제공받을 수 있어야 한다")
        void should_be_able_to_get_numberOfTotalElements_by_current() {
            var total = 9083409;
            when(page.getNumberOfElements()).thenReturn(total);
            response = HttpResponse.of("TEST", page);

            var result = response.getMeta().getCurrent();

            assertThat(result).isEqualTo(total);
        }
    }
}
