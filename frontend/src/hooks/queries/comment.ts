import { useMutation, useQuery, useQueryClient } from 'react-query';
import { createCommentRequest, deleteComment, editComment, getComments } from '../../apis/comments';

export const useFetchComments = (studylogId: number) =>
  useQuery(['comments', studylogId], () => getComments(studylogId));

export const useCreateComment = (studylogId: number) => {
  const queryClient = useQueryClient();

  return useMutation((body: { content: string }) => createCommentRequest({ studylogId, body }), {
    onSuccess() {
      queryClient.invalidateQueries(['comments', studylogId]);
    },
  });
};

export const useEditCommentMutation = (studylogId: number) => {
  const queryClient = useQueryClient();

  return useMutation(
    ({ commentId, body }: { commentId: number; body: { content: string } }) =>
      editComment({ studylogId, commentId, body }),
    {
      onSuccess() {
        queryClient.invalidateQueries(['comments', studylogId]);
      },
    }
  );
};

export const useDeleteCommentMutation = (studylogId: number) => {
  const queryClient = useQueryClient();

  return useMutation((commentId: number) => deleteComment({ studylogId, commentId }), {
    onSuccess() {
      queryClient.invalidateQueries(['comments', studylogId]);
    },
  });
};
