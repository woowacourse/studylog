/** @jsxImportSource @emotion/react */

import { css } from '@emotion/react';
import { useState } from 'react';
import { ReactComponent as CaretLeftIcon } from '../../assets/images/caret-left.svg';
import { ReactComponent as CaretRightIcon } from '../../assets/images/caret-right.svg';
import PopularStudylogItem from '../../components/Items/PopularStudylogItem';
import MEDIA_QUERY from '../../constants/mediaQuery';
import useScreenMediaQuery from '../../hooks/useScreenMediaQuery';
import { studyLogCategory, StudyLogResponse } from '../../models/Studylogs';
import { AlignItemsCenterStyle, FlexStyle } from '../../styles/flex.styles';
import { ResetScrollBar } from '../../styles/reset.styles';
import type { ValueOf } from '../../types/utils';
import { getKeyByValue } from '../../utils/object';
import { PopularStudylogListButtonIcon, PopularStudylogListStyle, SectionHeaderGapStyle, StyledChip } from './styles';

type Category = ValueOf<typeof studyLogCategory>;

const PopularStudyLogList = ({ studylogs }: { studylogs: StudyLogResponse }): JSX.Element => {
  const [selectedCategory, setSelectedCategory] = useState<Category>(studyLogCategory.allResponse);
  const popularStudyLogs = studylogs[getKeyByValue(studyLogCategory, selectedCategory)].data;

  const { isSm, isLg } = useScreenMediaQuery();

  const itemsPerPage = isSm ? 1 : isLg ? 2 : 3;
  const minPage = 1;
  const maxPage = Math.ceil(popularStudyLogs.length / itemsPerPage);
  const [currentPage, setPage] = useState(1);
  const page = Math.max(minPage, Math.min(maxPage, currentPage));

  const paginatedPopularStudyLogs = popularStudyLogs.slice(itemsPerPage * (page - 1), itemsPerPage * page);

  const increasePage = () => {
    setPage(Math.min(maxPage, page + 1));
  };

  const decreasePage = () => {
    setPage(Math.max(minPage, page - 1));
  };

  return (
    <section
      css={css`
        width: 100%;
        position: relative;
      `}
    >
      <div
        css={[
          SectionHeaderGapStyle,
          FlexStyle,
          AlignItemsCenterStyle,
          css`
            ${MEDIA_QUERY.md} {
              flex-direction: column;
            }
          `,
        ]}
      >
        <h2>😎 인기있는 학습로그</h2>
        <ul
          css={[
            FlexStyle,
            ResetScrollBar,
            css`
              gap: 1.4rem;

              ${MEDIA_QUERY.xs} {
                width: 100%;
                overflow-x: scroll;
              }
            `,
          ]}
        >
          {Object.values(studyLogCategory).map((item) => (
            <li key={item}>
              <StyledChip
                active={selectedCategory === item}
                onClick={() => setSelectedCategory(item)}
              >
                {item}
              </StyledChip>
            </li>
          ))}
        </ul>
      </div>
      <div css={css`
        display: flex;

        & > ul {
          flex: 1;
        }
      `}>
        <button onClick={decreasePage} css={css`
          padding: 0 2rem 0 0;
          opacity: ${page === minPage ? 0.15 : 'initial'};

          ${MEDIA_QUERY.xs} {
            padding: 0 1rem 0 0;
          }
        `}>
          <CaretLeftIcon css={PopularStudylogListButtonIcon} />
        </button>
        <ul css={PopularStudylogListStyle}>
          {paginatedPopularStudyLogs.map((studylog) => (
            <li key={studylog.id}>
              <PopularStudylogItem item={studylog} />
            </li>
          ))}
        </ul>
        <button onClick={increasePage} css={css`
          padding: 0 0 0 2rem;
          opacity: ${page === maxPage ? 0.15 : 'initial'};

          ${MEDIA_QUERY.xs} {
            padding: 0 0 0 1rem;
          }
        `}>
          <CaretRightIcon css={PopularStudylogListButtonIcon} />
        </button>
      </div>
    </section>
  );
};

export default PopularStudyLogList;
