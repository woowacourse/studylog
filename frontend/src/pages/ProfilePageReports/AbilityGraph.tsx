import DonutChart from '../../components/Charts/DonutChart';
import DonutChartForm from '../../components/Charts/DonutChartForm';
import { COLOR } from '../../constants';
import { Content, Section } from './AbilityGraph.styles';

type Ability = {
  id: number;
  name: string;
  weight: number;
  percentage: number;
  color: string;
  present: boolean;
};

const MODE = {
  VIEW: 'VIEW',
  EDIT: 'EDIT',
  NEW: 'NEW',
};

const AbilityGraph = ({ abilities, mode }: { abilities: Ability[]; mode: string }) => {
  return (
    <Section>
      <h3>📊 역량 그래프</h3>
      <Content>
        {mode === MODE.VIEW && (
          <DonutChart
            chartData={{
              title: '역량 그래프',
              categoryTitle: '역량',
              data: abilities.filter((item) => item.present),
            }}
            config={{ backgroundColor: COLOR.WHITE, width: 400, height: 220 }}
          />
        )}
        {mode === MODE.NEW && (
          <DonutChartForm
            chartData={{
              title: '역량 그래프',
              categoryTitle: '역량',
              data: abilities.filter((item) => item.present),
            }}
            config={{ backgroundColor: COLOR.WHITE, width: 400, height: 220 }}
          />
        )}
      </Content>
    </Section>
  );
};

export default AbilityGraph;
