package code.ds;

import java.util.*;

public class Calendar {

    private List<Pair<Pair<Date,Integer>,Boolean>> _scheEvents = new ArrayList<>();
    // sample
    public Calendar() {
    }

    // Should allow multiple events to be scheduled over the same time window.
    public void schedule(Event event) {
        // IMPLEMENT ME
        if(event != null){
            _scheEvents.add(new Pair<Pair<Date, Integer>,Boolean>(new Pair<Date,Integer>(event.startDate, event.id),true));
            _scheEvents.add(new Pair<Pair<Date, Integer>,Boolean>(new Pair<Date,Integer>(event.endDate, event.id),false));
        }
    }

    public List<ConflictedTimeWindow> findConflictedTimeWindow() {
        // IMPLEMENT ME
        Collections.sort(_scheEvents, new Comparator<Pair<Pair<Date, Integer>, Boolean>>(){
            @Override
            public int compare(final Pair<Pair<Date, Integer>, Boolean> o1, final Pair<Pair<Date, Integer>, Boolean> o2){
                if(o1.getFirst().getFirst().compareTo(o2.getFirst().getFirst()) == 0){
                    return o1.getFirst().getSecond().compareTo(o2.getFirst().getSecond());
                }
                return o1.getFirst().getFirst().compareTo(o2.getFirst().getFirst());
            }
        });
        List<ConflictedTimeWindow> output = new ArrayList<ConflictedTimeWindow>();
        Set<Integer> ongoing = new HashSet<>();
        ongoing.add(_scheEvents.get(0).getFirst().getSecond());
        Date start = _scheEvents.get(0).getFirst().getFirst();
        for(int i = 1; i < _scheEvents.size(); i++){
            if(_scheEvents.get(i).getSecond()){
                ongoing.add(_scheEvents.get(i).getFirst().getSecond());
                start = _scheEvents.get(i).getFirst().getFirst();

                if(ongoing.size() > 1 && !start.equals(_scheEvents.get(i+1).getFirst().getFirst())){
                    Set<Integer> temp = new HashSet<Integer>();
                    temp.addAll(ongoing);
                    ConflictedTimeWindow res = new ConflictedTimeWindow(start, _scheEvents.get(i + 1).getFirst().getFirst(), temp);
                    output.add(res);
                    start = _scheEvents.get(i + 1).getFirst().getFirst();
                }
            }
            else{
                if(ongoing.size() > 1 && !start.equals(_scheEvents.get(i).getFirst().getFirst())){
                    Set<Integer> temp = new HashSet<Integer>();
                    temp.addAll(ongoing);
                    ConflictedTimeWindow res2 = new ConflictedTimeWindow(start, _scheEvents.get(i).getFirst().getFirst(), temp);
                    output.add(res2);
                    start = _scheEvents.get(i).getFirst().getFirst();
                }
                ongoing.remove(_scheEvents.get(i).getFirst().getSecond());
            }
        }
        return output;
    }

    public static class ConflictedTimeWindow {
        private final Date startDate;
        private final Date endDate;
        private final Set<Integer> conflictedEventIds;

        public ConflictedTimeWindow(Date startDate, Date endDate, Set<Integer> conflictedEventIds) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.conflictedEventIds = conflictedEventIds;
        }

        public Date getStartDate() {
            return startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public Set<Integer> getConflictedEventIds() {
            return conflictedEventIds;
        }

        @Override
        public String toString() {
            return "ConflictedTimeWindow{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", conflictedEventIds=" + conflictedEventIds +
                    '}';
        }
    }

    public static class Event {
        private final int id;
        private final Date startDate;
        private final Date endDate;

        public Event(int id, Date startDate, Date endDate) {
            this.id = id;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        public String toString() {
            return "Event{" +
                    "id=" + id +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    '}';
        }
    }
    public static class Pair<L,R> {
        private L first;
        private R second;
        public Pair(L l, R r){
            this.first = l;
            this.second = r;
        }
        public L getFirst(){ return first; }
        public R getSecond(){ return second; }
        public void setFirst(L l){ this.first = l; }
        public void setSecond(R r){ this.second = r; }
    }

    public static void main(String[] args) {
        Calendar calendar = new Calendar();

        calendar.schedule(new Event(1, new Date(114, 0, 1, 10, 0), new Date(114, 0, 1, 11, 0))); // 2014-01-01 10:00 - 11:00
        calendar.schedule(new Event(2, new Date(114, 0, 1, 11, 0), new Date(114, 0, 1, 12, 0))); // 2014-01-01 11:00 - 12:00
        calendar.schedule(new Event(3, new Date(114, 0, 1, 12, 0), new Date(114, 0, 1, 13, 0))); // 2014-01-01 12:00 - 13:00

        calendar.schedule(new Event(4, new Date(114, 0, 2, 10, 0), new Date(114, 0, 2, 11, 0))); // 2014-01-02 10:00 - 11:00
        calendar.schedule(new Event(5, new Date(114, 0, 2, 9, 30), new Date(114, 0, 2, 11, 30))); // 2014-01-02 09:30 - 11:30
        calendar.schedule(new Event(6, new Date(114, 0, 2, 8, 30), new Date(114, 0, 2, 12, 30))); // 2014-01-02 08:30 - 12:30

        calendar.schedule(new Event(7, new Date(114, 0, 3, 10, 0), new Date(114, 0, 3, 11, 0))); // 2014-01-03 10:00 - 11:00
        calendar.schedule(new Event(8, new Date(114, 0, 3, 9, 30), new Date(114, 0, 3, 10, 30))); // 2014-01-03 09:30 - 10:30
        calendar.schedule(new Event(9, new Date(114, 0, 3, 9, 45), new Date(114, 0, 3, 10, 45))); // 2014-01-03 09:45 - 10:45

        List<ConflictedTimeWindow> conflictedTimeWindows = calendar.findConflictedTimeWindow();
        System.out.println(conflictedTimeWindows);
        // should print something like
        // [ConflictedTimeWindow{startDate=Thu Jan 02 09:30:00 PST 2014, endDate=Thu Jan 02 10:00:00 PST 2014, conflictedEventIds=[5, 6]},
        //  ConflictedTimeWindow{startDate=Thu Jan 02 10:00:00 PST 2014, endDate=Thu Jan 02 11:00:00 PST 2014, conflictedEventIds=[4, 5, 6]},
        //  ConflictedTimeWindow{startDate=Thu Jan 02 11:00:00 PST 2014, endDate=Thu Jan 02 11:30:00 PST 2014, conflictedEventIds=[5, 6]},
        //  ConflictedTimeWindow{startDate=Fri Jan 03 09:45:00 PST 2014, endDate=Fri Jan 03 10:00:00 PST 2014, conflictedEventIds=[8, 9]},
        //  ConflictedTimeWindow{startDate=Fri Jan 03 10:00:00 PST 2014, endDate=Fri Jan 03 10:30:00 PST 2014, conflictedEventIds=[7, 8, 9]},
        //  ConflictedTimeWindow{startDate=Fri Jan 03 10:30:00 PST 2014, endDate=Fri Jan 03 10:45:00 PST 2014, conflictedEventIds=[7, 9]}]
    }

}
